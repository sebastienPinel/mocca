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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.buergerkarte.namespaces.securitylayer._1.InfoboxReadRequestType;
import at.gv.egiz.bku.slcommands.InfoboxReadCommand;
import at.gv.egiz.bku.slcommands.SLCommandContext;
import at.gv.egiz.bku.slcommands.SLResult;
import at.gv.egiz.bku.slexceptions.SLCommandException;

/**
 * This class implements the security layer command
 * <code>InfoboxReadRequest</code>.
 * <p>
 * <b>NOTE:</b> Currently the only supported infobox identifier is '
 * <code>IdentityLink</code>'.
 * </p>
 * 
 * @author mcentner
 */
public class InfoboxReadCommandImpl extends AbstractInfoboxCommandImpl<InfoboxReadRequestType> implements
    InfoboxReadCommand {
  
  /**
   * Logging facility.
   */
  protected final Logger log = LoggerFactory.getLogger(InfoboxReadCommandImpl.class);
  
  @Override
  public String getName() {
    return "InfoboxReadRequest";
  }

  @Override
  protected String getInfoboxIdentifier(InfoboxReadRequestType request) {
    return request.getInfoboxIdentifier();
  }

  @Override
  public void init(Object request) throws SLCommandException {
    super.init(request);
    
    InfoboxReadRequestType req = getRequestValue();
    
    if (req.getAssocArrayParameters() != null && 
        !(infobox instanceof AssocArrayInfobox)) {
      log.info("Got AssocArrayParameters but Infobox type is not AssocArray.");
      throw new SLCommandException(4010);
    }
    
    if (req.getBinaryFileParameters() != null &&
        !(infobox instanceof BinaryFileInfobox)) {
      log.info("Got BinaryFileParameters but Infobox type is not BinaryFile.");
      throw new SLCommandException(4010);
    }
    
  }

  @Override
  public SLResult execute(SLCommandContext commandContext) {
    
    try {
      return infobox.read(getRequestValue(), commandContext);
    } catch (SLCommandException e) {
      return new ErrorResultImpl(e, commandContext.getLocale());
    }
    
  }

  @Override
  public String getIdentityLinkDomainId() {
    if (infobox instanceof IdentityLinkInfoboxImpl) {
      return ((IdentityLinkInfoboxImpl) infobox).getDomainIdentifier();
    } else {
      return null;
    }
  }

}
