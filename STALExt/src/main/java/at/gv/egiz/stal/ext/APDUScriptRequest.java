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


package at.gv.egiz.stal.ext;

import java.util.List;

import at.gv.egiz.stal.STALRequest;

public class APDUScriptRequest extends STALRequest {

  public static abstract class RequestScriptElement {

  }

  public static class Reset extends RequestScriptElement {

  }
  
  public static class Command extends RequestScriptElement {
    
    private int sequence;
    
    private byte[] commandAPDU; 

    private byte[] expectedSW;

    public Command(int sequence, byte[] commandAPDU, byte[] expectedSW) {
      this.sequence = sequence;
      this.commandAPDU = commandAPDU;
      this.expectedSW = expectedSW;
    }
    
    public int getSequence() {
      return sequence;
    }

    public byte[] getCommandAPDU() {
      return commandAPDU;
    }

    public byte[] getExpectedSW() {
      return expectedSW;
    }
    
  }

  private List<RequestScriptElement> script;
  
  public APDUScriptRequest(List<RequestScriptElement> script) {
    super();
    this.script = script;
  }

  public List<RequestScriptElement> getScript() {
    return script;
  }
  
}
