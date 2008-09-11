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
/**
 * 
 */
package at.gv.egiz.bku.slxhtml.css;

import org.w3c.css.properties.css1.CssBackgroundCSS2;
import org.w3c.css.util.ApplContext;
import org.w3c.css.util.InvalidParamException;
import org.w3c.css.values.CssExpression;
import org.w3c.css.values.CssValue;

/**
 * @author mcentner
 *
 */
public class CssBackgroundSLXHTML extends CssBackgroundCSS2 {

  public CssBackgroundSLXHTML() {
  }

  public CssBackgroundSLXHTML(ApplContext ac, CssExpression expression,
      boolean check) throws InvalidParamException {
    super(ac, expression, check);
    
    // A Citizen Card Environment must support all the options for specifying a
    // colour listed in [CSS 2], section 4.3.6 for a CSS property, if such an
    // option is available for this property according to [CSS 2].

    // The exceptions are the system colours (cf. [CSS 2], section 18.2); these
    // must not be used in an instance document so as to prevent dependencies on
    // the system environment. Otherwise the instance document must be rejected
    // by the Citizen Card Environment.
    
    CssValue color = getColor();
    if (!isSoftlyInherited() && color != null) {
      if (CssColorSLXHTML.isDisallowedColor(color)) {
        throw new SLXHTMLInvalidParamException("color", color, getPropertyName(), ac);
      }
    }

    // The properties for selecting and controlling an image as background
    // (background-image, background-repeat, background-position,
    // background-attachment; cf. [CSS 2], section 14.2.1) must not be contained
    // in an instance document to prevent content from overlapping. Otherwise
    // the instance document must be rejected by the Citizen Card Environment.
    //
    // The property for the shorthand version of the background properties
    // (background) should be supported by a Citizen Card Environment. The
    // recommended values result from the explanations for the background-color
    // property above (cf. [CSS 2], section 14.2.1). If the property contains
    // values for selecting and controlling an image as background, the instance
    // document must be rejected by the Citizen Card Environment.
    
    if (getImage() != null) {
      throw new SLXHTMLInvalidParamException("background", "background-image", ac);
    }
    
    if (getRepeat() != null) {
      throw new SLXHTMLInvalidParamException("background", "background-repeat", ac);
    }
    
    if (getPosition() != null) {
      throw new SLXHTMLInvalidParamException("background", "background-position", ac);
    }

    if (getAttachment() != null) {
      throw new SLXHTMLInvalidParamException("background", "background-attachment", ac);
    }
    
  }

  public CssBackgroundSLXHTML(ApplContext ac, CssExpression expression)
      throws InvalidParamException {
    this(ac, expression, false);
  }

}
