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

package moaspss.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VerifyCMSSignatureResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VerifyCMSSignatureResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element name="SignerInfo" type="{http://www.w3.org/2000/09/xmldsig#}KeyInfoType"/>
 *         &lt;element name="SignatureCheck" type="{http://reference.e-government.gv.at/namespace/moa/20020822#}CheckResultType"/>
 *         &lt;element name="CertificateCheck" type="{http://reference.e-government.gv.at/namespace/moa/20020822#}CheckResultType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VerifyCMSSignatureResponseType", propOrder = {
    "signerInfoAndSignatureCheckAndCertificateCheck"
})
public class VerifyCMSSignatureResponseType {

    @XmlElementRefs({
        @XmlElementRef(name = "CertificateCheck", namespace = "http://reference.e-government.gv.at/namespace/moa/20020822#", type = JAXBElement.class),
        @XmlElementRef(name = "SignerInfo", namespace = "http://reference.e-government.gv.at/namespace/moa/20020822#", type = JAXBElement.class),
        @XmlElementRef(name = "SignatureCheck", namespace = "http://reference.e-government.gv.at/namespace/moa/20020822#", type = JAXBElement.class)
    })
    protected List<JAXBElement<?>> signerInfoAndSignatureCheckAndCertificateCheck;

    /**
     * Gets the value of the signerInfoAndSignatureCheckAndCertificateCheck property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signerInfoAndSignatureCheckAndCertificateCheck property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignerInfoAndSignatureCheckAndCertificateCheck().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link KeyInfoType }{@code >}
     * {@link JAXBElement }{@code <}{@link CheckResultType }{@code >}
     * {@link JAXBElement }{@code <}{@link CheckResultType }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getSignerInfoAndSignatureCheckAndCertificateCheck() {
        if (signerInfoAndSignatureCheckAndCertificateCheck == null) {
            signerInfoAndSignatureCheckAndCertificateCheck = new ArrayList<JAXBElement<?>>();
        }
        return this.signerInfoAndSignatureCheckAndCertificateCheck;
    }

}
