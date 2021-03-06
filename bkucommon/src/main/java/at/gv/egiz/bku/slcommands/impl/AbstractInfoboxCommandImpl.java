/*
 * Copyright 2011 by Graz University of Technology, Austria
 * MOCCA has been developed by the E-Government Innovation Center EGIZ, a joint
 * initiative of the Federal Chancellery Austria and Graz University of Technology.
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * http://www.osor.eu/eupl/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * This product combines work with different licenses. See the "NOTICE" text
 * file for details on the various modules and licenses.
 * The "NOTICE" text file is part of the distribution. Any derivative works
 * that you distribute must include a readable copy of the "NOTICE" text file.
 */


package at.gv.egiz.bku.slcommands.impl;

import at.gv.egiz.bku.slexceptions.SLCommandException;

/**
 * An abstract base class for implementations of security layer infobox requests.
 * 
 * @author mcentner
 *
 * @param <T>
 */
public abstract class AbstractInfoboxCommandImpl<T> extends SLCommandImpl<T> {

  /**
   * The infobox implementation.
   */
  protected Infobox infobox;

  /**
   * The infobox factory.
   */
  protected InfoboxFactory infoboxFactory;
  
  /**
   * @return the infoboxFactory
   */
  public InfoboxFactory getInfoboxFactory() {
    return infoboxFactory;
  }

  /**
   * @param infoboxFactory the infoboxFactory to set
   */
  public void setInfoboxFactory(InfoboxFactory infoboxFactory) {
    this.infoboxFactory = infoboxFactory;
  }
  
  @Override
  public void init(Object request)
      throws SLCommandException {
    super.init(request);
    
    String infoboxIdentifier = getInfoboxIdentifier(getRequestValue());
    
    infobox = infoboxFactory.createInfobox(infoboxIdentifier);
  }
  
  /**
   * Returns the infobox identifier given in <code>request</code>.
   * 
   * @param request the request value
   * 
   * @return the infobox identifier givne in <code>request</code>
   */
  protected abstract String getInfoboxIdentifier(T request);


  public String getInfoboxIdentifier() {
    if (infobox != null) {
      return infobox.getIdentifier();
    } else {
      return null;
    }
  }
  
}
