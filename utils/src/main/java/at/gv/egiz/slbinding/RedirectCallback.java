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


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.gv.egiz.slbinding;

import java.io.ByteArrayOutputStream;
import javax.xml.stream.XMLStreamException;

/**
 * 
 * The beforeUnmarshal(Unmarshaller um, Object parent) methods don't allow to pass the RedirectEventFilter,
 * so we implement a callback interface common to all generated classes
 * @author clemens
 */
public interface RedirectCallback {

    void enableRedirect(RedirectEventFilter filter) throws XMLStreamException;

    void disableRedirect(RedirectEventFilter filter) throws XMLStreamException;

    /**
     * @return the redirected stream or null if enableRedirect() was not called before
     */
    ByteArrayOutputStream getRedirectedStream();
}
