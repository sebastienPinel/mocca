/*
 * Copyright 2008 Federal Chancellery Austria and
 * Graz University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package at.gv.egiz.bku.local.webapp;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.gv.egiz.bku.binding.BindingProcessorFuture;
import at.gv.egiz.bku.binding.BindingProcessorManager;
import at.gv.egiz.bku.binding.HTTPBindingProcessorImpl;
import at.gv.egiz.bku.binding.HttpUtil;
import at.gv.egiz.bku.binding.Id;
import at.gv.egiz.bku.binding.IdFactory;
import at.gv.egiz.bku.binding.InputDecoderFactory;
import at.gv.egiz.org.apache.tomcat.util.http.AcceptLanguage;

public class BKURequestHandler extends SpringBKUServlet {

  private static final long serialVersionUID = 1L;

  public final static String ENCODING = "UTF-8";

	private final Logger log = LoggerFactory.getLogger(BKURequestHandler.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, java.io.IOException {

        String acceptLanguage = req.getHeader("Accept-Language");
        Locale locale = AcceptLanguage.getLocale(acceptLanguage);
        log.info("Received request. Accept-Language locale: {}.", locale);

        BindingProcessorManager bindingProcessorManager = getBindingProcessorManager();
        
        HTTPBindingProcessorImpl bindingProcessor;
        bindingProcessor = (HTTPBindingProcessorImpl) bindingProcessorManager
            .createBindingProcessor("HTTP", locale);
        Map<String, String> headerMap = new HashMap<String, String>();
        for (Enumeration<?> headerName = req.getHeaderNames(); headerName
            .hasMoreElements();) {
          String header = (String) headerName.nextElement();
          if (header != null) {
            headerMap.put(header, req.getHeader(header));
          }
        }
        
        InputStream inputStream;
        String charset;
        if (req.getMethod().equals("POST")) {
          charset = req.getCharacterEncoding();
          String contentType = req.getContentType();
          if (charset != null) {
            contentType += ";" + charset;
          }
          headerMap.put(HttpUtil.HTTP_HEADER_CONTENT_TYPE, contentType);
          inputStream = req.getInputStream();
        } else {
          charset = "UTF-8";
          headerMap.put(HttpUtil.HTTP_HEADER_CONTENT_TYPE,
              InputDecoderFactory.URL_ENCODED);
          String queryString = req.getQueryString();
          if (queryString != null) {
            inputStream = new ByteArrayInputStream(queryString.getBytes(charset));
          } else {
            inputStream = new ByteArrayInputStream(new byte[] {});
          }
        }
        bindingProcessor.setHTTPHeaders(headerMap);
        bindingProcessor.consumeRequestStream(req.getRequestURL().toString(), inputStream);
        req.getInputStream().close();

        String redirectURL = bindingProcessor.getRedirectURL();

        Id id = IdFactory.getInstance().createId();
        BindingProcessorFuture bindingProcessorFuture = bindingProcessorManager
            .process(id, bindingProcessor);

        if (redirectURL != null) {
          // send redirect and return
          resp.sendRedirect(redirectURL);
          return;
        }
        
        // wait for the binding processor to finish processing
        try {
          bindingProcessorFuture.get();
        } catch (InterruptedException e) {
          resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
          return;
        } catch (ExecutionException e) {
          log.error("Request processing failed.", e);
          resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
          return;
        }
        
		resp.setStatus(bindingProcessor.getResponseCode());

		// set response headers
		Map<String, String> responseHeaders = bindingProcessor.getResponseHeaders();
		for (String header : responseHeaders.keySet()) {
		  resp.setHeader(header, responseHeaders.get(header));
		}
		String serverHeader = bindingProcessor.getServerHeaderValue();
		if (serverHeader != null && !serverHeader.isEmpty()) {
		  resp.setHeader(HttpUtil.HTTP_HEADER_SERVER, serverHeader);
		}
		String signatureLayout = bindingProcessor.getSignatureLayoutHeaderValue();
		if (signatureLayout != null && !signatureLayout.isEmpty()) {
		  resp.setHeader("SignatureLayout", signatureLayout);
		}
		
		resp.setContentType(bindingProcessor.getResultContentType());
		resp.setCharacterEncoding(ENCODING);
		bindingProcessor.writeResultTo(resp.getOutputStream(), ENCODING);
		
		resp.getOutputStream().flush();
		resp.getOutputStream().close();
		log.debug("Finished Request.");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, java.io.IOException {
		doPost(req, resp);
	}
	
	
}
