//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.26 at 12:32:35 PM MEZ 
//


package at.buergerkarte.namespaces.securitylayer._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for CreateXMLSignatureRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateXMLSignatureRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="KeyboxIdentifier" type="{http://www.buergerkarte.at/namespaces/securitylayer/1.2#}BoxIdentifierType"/>
 *         &lt;element name="DataObjectInfo" type="{http://www.buergerkarte.at/namespaces/securitylayer/1.2#}DataObjectInfoType" maxOccurs="unbounded"/>
 *         &lt;element name="SignatureInfo" type="{http://www.buergerkarte.at/namespaces/securitylayer/1.2#}SignatureInfoCreationType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateXMLSignatureRequestType", propOrder = {
    "keyboxIdentifier",
    "dataObjectInfo",
    "signatureInfo"
})
public class CreateXMLSignatureRequestType {

    @XmlElement(name = "KeyboxIdentifier", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String keyboxIdentifier;
    @XmlElement(name = "DataObjectInfo", required = true)
    protected List<DataObjectInfoType> dataObjectInfo;
    @XmlElement(name = "SignatureInfo")
    protected SignatureInfoCreationType signatureInfo;

    /**
     * Gets the value of the keyboxIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyboxIdentifier() {
        return keyboxIdentifier;
    }

    /**
     * Sets the value of the keyboxIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyboxIdentifier(String value) {
        this.keyboxIdentifier = value;
    }

    /**
     * Gets the value of the dataObjectInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataObjectInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataObjectInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataObjectInfoType }
     * 
     * 
     */
    public List<DataObjectInfoType> getDataObjectInfo() {
        if (dataObjectInfo == null) {
            dataObjectInfo = new ArrayList<DataObjectInfoType>();
        }
        return this.dataObjectInfo;
    }

    /**
     * Gets the value of the signatureInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SignatureInfoCreationType }
     *     
     */
    public SignatureInfoCreationType getSignatureInfo() {
        return signatureInfo;
    }

    /**
     * Sets the value of the signatureInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureInfoCreationType }
     *     
     */
    public void setSignatureInfo(SignatureInfoCreationType value) {
        this.signatureInfo = value;
    }

}
