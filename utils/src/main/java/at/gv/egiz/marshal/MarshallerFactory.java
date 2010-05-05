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
package at.gv.egiz.marshal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Clemens Orthacker <clemens.orthacker@iaik.tugraz.at>
 */
public class MarshallerFactory {

  public static Marshaller createMarshaller(JAXBContext ctx, boolean formattedOutput, boolean fragment) throws JAXBException {
    Logger log = LoggerFactory.getLogger(MarshallerFactory.class);
    Marshaller m = ctx.createMarshaller();
    try {
      if (formattedOutput) {
        log.trace("Setting marshaller property FORMATTED_OUTPUT.");
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      }
      if (fragment) {
        log.trace("Setting marshaller property FRAGMENT.");
        m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
      }
      log.trace("Setting marshaller property NamespacePrefixMapper.");
      m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapperImpl());
    } catch (PropertyException ex) {
      log.info("Failed to set marshaller property: {}.", ex.getMessage());
    }
    return m;
  }
  
  public static Marshaller createMarshaller(JAXBContext ctx, boolean formattedOutput) throws JAXBException {
    return createMarshaller(ctx, formattedOutput, false);
  }

  public static Marshaller createMarshaller(JAXBContext ctx) throws JAXBException {
    return createMarshaller(ctx, false, false);
  }
}
