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
package at.gv.egiz.bku.smccstal;

import at.gv.egiz.bku.gui.BKUGUIFacade;
import at.gv.egiz.smcc.SignatureCard;
import at.gv.egiz.smcc.util.SMCCHelper;
import at.gv.egiz.stal.ErrorResponse;
import at.gv.egiz.stal.STALRequest;
import at.gv.egiz.stal.STALResponse;
import at.gv.egiz.stal.StatusRequest;
import at.gv.egiz.stal.StatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Clemens Orthacker <clemens.orthacker@iaik.tugraz.at>
 */
public class StatusRequestHandler extends AbstractRequestHandler {

  private final Logger log = LoggerFactory.getLogger(StatusRequestHandler.class);

  @Override
  public void init(SignatureCard sc, BKUGUIFacade gui) {
    //nothing, avoid NullPointerEx since requireCard==false => sc==null
  }


  @Override
  public STALResponse handleRequest(STALRequest request) throws InterruptedException {

    if (request instanceof StatusRequest) {
      log.info("Handling STATUS request.");
      SMCCHelper smccHelper = new SMCCHelper();
      StatusResponse response = new StatusResponse();
      if (log.isTraceEnabled()) {
        log.trace("SMCC result code: {}, cardReady: {}.", smccHelper
            .getResultCode(),
            (smccHelper.getResultCode() == SMCCHelper.CARD_FOUND));
      }
      if (smccHelper.getResultCode() == SMCCHelper.CARD_FOUND) {
        response.setCardReady(Boolean.TRUE);
      } else {
        response.setCardReady(Boolean.FALSE);
      }
      return response;
    } else {
      log.error("Got unexpected STAL request: {}.", request);
      return new ErrorResponse(1000);
    }
  }

  @Override
  public boolean requireCard() {
    return false;
  }
}
