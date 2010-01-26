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
package at.gv.egiz.smcc;

public class CardNotSupportedException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Creates a new instance of this <code>CardNotSupportedException</code>.
   * 
   */
  public CardNotSupportedException() {
    super();
  }

  /**
   * Creates a new instance of this <code>CardNotSupportedException</code>.
   * 
   * @param message
   * @param cause
   */
  public CardNotSupportedException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Creates a new instance of this <code>CardNotSupportedException</code>.
   * 
   * @param message
   */
  public CardNotSupportedException(String message) {
    super(message);
  }

  /**
   * Creates a new instance of this <code>CardNotSupportedException</code>.
   * 
   * @param cause
   */
  public CardNotSupportedException(Throwable cause) {
    super(cause);
  }

}
