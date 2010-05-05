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
package at.gv.egiz.bku.slcommands.impl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Templates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import at.buergerkarte.namespaces.securitylayer._1.CreateXMLSignatureResponseType;
import at.buergerkarte.namespaces.securitylayer._1.ObjectFactory;
import at.gv.egiz.bku.slcommands.CreateXMLSignatureResult;
import at.gv.egiz.bku.slcommands.SLMarshallerFactory;
import at.gv.egiz.bku.slexceptions.SLRuntimeException;

/**
 * This calls implements the result of the security layer command <code>CreateXMLSignature</code>.
 * 
 * @author mcentner
 */
public class CreateXMLSignatureResultImpl extends SLResultImpl implements CreateXMLSignatureResult {

  /**
   * Logging facility.
   */
  private final Logger log = LoggerFactory.getLogger(CreateXMLSignatureResultImpl.class);
  
  /**
   * The document containing the XMLSignature.
   */
  protected Document doc;
  
  /**
   * The content of the CreateXMLSignatureResponse.
   */
  protected Element content;
  
  /**
   * Creates a new instance of this CreateXMLSignatureResultImpl with the given
   * signature <code>document</code>.
   * 
   * @param document the signature document
   * 
   * @throws NullPointerException if <code>document</code> is <code>null</code>
   */
  public CreateXMLSignatureResultImpl(Document document) {
    super();
    
    if (document == null) {
      throw new NullPointerException("Argument 'document' must not be null.");
    }
    
    this.doc = document;
    this.content = document.getDocumentElement();
    
    marshallCreateXMLSignatureResponse();
  }

  /**
   * Marshalls the <code>CreateXMLSignatureResponse</code>. 
   */
  private void marshallCreateXMLSignatureResponse() {

    ObjectFactory factory = new ObjectFactory();
    
    at.gv.egiz.slbinding.impl.CreateXMLSignatureResponseType createCreateXMLSignatureResponseType = factory.createCreateXMLSignatureResponseType();
    JAXBElement<CreateXMLSignatureResponseType> createCreateXMLSignatureResponse = factory.createCreateXMLSignatureResponse(createCreateXMLSignatureResponseType);

    DocumentFragment fragment = doc.createDocumentFragment();

    Marshaller marshaller = SLMarshallerFactory.getInstance().createMarshaller(false);
    try {
      marshaller.marshal(createCreateXMLSignatureResponse, fragment);
    } catch (JAXBException e) {
      log.error("Failed to marshall 'CreateXMLSignatureResponse'.", e);
      throw new SLRuntimeException(e);
    }

    Node child = fragment.getFirstChild();
    if (child instanceof Element) {
      child.appendChild(doc.replaceChild(child, content));
    }
    
  }

  @Override
  public void writeTo(Result result, Templates templates, boolean fragment) {
    writeTo(doc, result, templates, fragment);
  }

  @Override
  public Element getContent() {
    return content;
  }

}
