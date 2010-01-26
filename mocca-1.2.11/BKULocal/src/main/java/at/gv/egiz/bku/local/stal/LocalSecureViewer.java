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
package at.gv.egiz.bku.local.stal;

import at.gv.egiz.bku.slcommands.impl.DataObjectHashDataInput;
import at.gv.egiz.bku.smccstal.SecureViewer;
import java.io.IOException;
import java.util.ArrayList;

import at.gv.egiz.bku.gui.BKUGUIFacade;
import at.gv.egiz.stal.HashDataInput;
import at.gv.egiz.stal.impl.ByteArrayHashDataInput;
import at.gv.egiz.stal.signedinfo.ReferenceType;
import at.gv.egiz.stal.signedinfo.SignedInfoType;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Clemens Orthacker <clemens.orthacker@iaik.tugraz.at>
 */
public class LocalSecureViewer implements SecureViewer {

  private static final Log log = LogFactory.getLog(LocalSignRequestHandler.class);
  private List<HashDataInput> hashDataInputs = Collections.EMPTY_LIST;

  protected BKUGUIFacade gui;

  public LocalSecureViewer(BKUGUIFacade gui) {
    this.gui = gui;
  }

  public void setDataToBeSigned(List<HashDataInput> dataToBeSigned) {
    this.hashDataInputs = dataToBeSigned;
  }

  /**
   *
   * @param dsigReferences
   * @throws java.lang.Exception
   */
  @Override
  public void displayDataToBeSigned(SignedInfoType signedInfo,
          ActionListener okListener, String okCommand)
          throws Exception {
    if (signedInfo.getReference().size() == 0) {
      log.error("No hashdata input selected to be displayed: null");
      throw new Exception("No HashData Input selected to be displayed");
    }

    ArrayList<HashDataInput> selectedHashDataInputs = new ArrayList<HashDataInput>();
    for (ReferenceType dsigRef : signedInfo.getReference()) {
      // don't get Manifest, QualifyingProperties, ...
      if (dsigRef.getType() == null) {
        String dsigRefId = dsigRef.getId();
        if (dsigRefId != null) {
          boolean hdiAvailable = false;
          for (HashDataInput hashDataInput : hashDataInputs) {
            if (dsigRefId.equals(hashDataInput.getReferenceId())) {
              log.debug("display hashdata input for dsig:SignedReference " +
                      dsigRefId);
              selectedHashDataInputs.add(
                      ensureCachedHashDataInput(hashDataInput));
              hdiAvailable = true;
              break;
            }
          }
          if (!hdiAvailable) {
            log.error("no hashdata input for dsig:SignedReference " + dsigRefId);
            throw new Exception(
              "No HashDataInput available for dsig:SignedReference " + dsigRefId);
          }
        } else {
          throw new Exception(
            "Cannot get HashDataInput for dsig:Reference without Id attribute");
        }
      }
    }

    if (selectedHashDataInputs.size() < 1) {
      log.error("dsig:SignedInfo does not contain a data reference");
      throw new Exception("dsig:SignedInfo does not contain a data reference");
    }
    gui.showSecureViewer(selectedHashDataInputs, okListener, okCommand);
  }


  private HashDataInput ensureCachedHashDataInput(HashDataInput hashDataInput)
          throws IOException {
    if (!(hashDataInput instanceof DataObjectHashDataInput)) {
      
      log.warn("expected DataObjectHashDataInput for LocalSignRequestHandler, got " +
              hashDataInput.getClass().getName());

      InputStream hdIs = hashDataInput.getHashDataInput();
      ByteArrayOutputStream baos = new ByteArrayOutputStream(hdIs.available());
      int b;
      while ((b = hdIs.read()) != -1) {
        baos.write(b);
      }
      hashDataInput = new ByteArrayHashDataInput(baos.toByteArray(),
              hashDataInput.getReferenceId(),
              hashDataInput.getMimeType(),
              hashDataInput.getEncoding(),
              hashDataInput.getFilename());
    }
    return hashDataInput;
  }

}
