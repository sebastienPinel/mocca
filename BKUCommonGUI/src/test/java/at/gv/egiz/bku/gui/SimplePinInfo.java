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

package at.gv.egiz.bku.gui;

import at.gv.egiz.smcc.PinInfo;
import java.util.Locale;

/**
 *
 * @author Clemens Orthacker <clemens.orthacker@iaik.tugraz.at>
 */
public class SimplePinInfo extends PinInfo {

  String name;

  public SimplePinInfo(int minLength, int maxLength, String rexepPattern, String name, byte kid, byte[] contextAID, int maxRetries) {
    super(minLength, maxLength, rexepPattern, null, null, kid, contextAID, maxRetries);
    this.name = name;
  }

  @Override
  public String getLocalizedName() {
    return name;
  }

  @Override
  public String getLocalizedName(Locale locale) {
    return name;
  }

}
