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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CreateTransformsInfo" type="{http://reference.e-government.gv.at/namespace/moa/20020822#}TransformsInfoType"/>
 *         &lt;element ref="{http://reference.e-government.gv.at/namespace/moa/20020822#}Supplement" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "createTransformsInfo",
    "supplement"
})
@XmlRootElement(name = "CreateTransformsInfoProfile")
public class CreateTransformsInfoProfile {

    @XmlElement(name = "CreateTransformsInfo", required = true)
    protected TransformsInfoType createTransformsInfo;
    @XmlElement(name = "Supplement")
    protected List<XMLDataObjectAssociationType> supplement;

    /**
     * Gets the value of the createTransformsInfo property.
     * 
     * @return
     *     possible object is
     *     {@link TransformsInfoType }
     *     
     */
    public TransformsInfoType getCreateTransformsInfo() {
        return createTransformsInfo;
    }

    /**
     * Sets the value of the createTransformsInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransformsInfoType }
     *     
     */
    public void setCreateTransformsInfo(TransformsInfoType value) {
        this.createTransformsInfo = value;
    }

    /**
     * Gets the value of the supplement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supplement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupplement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XMLDataObjectAssociationType }
     * 
     * 
     */
    public List<XMLDataObjectAssociationType> getSupplement() {
        if (supplement == null) {
            supplement = new ArrayList<XMLDataObjectAssociationType>();
        }
        return this.supplement;
    }

}
