//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.26 at 12:32:35 PM MEZ 
//


package at.buergerkarte.namespaces.securitylayer._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VerifyHashInfoRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VerifyHashInfoRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HashData" type="{http://www.buergerkarte.at/namespaces/securitylayer/1.2#}HashDataType"/>
 *         &lt;element name="HashAlgorithm" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="FriendlyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HashValue" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VerifyHashInfoRequestType", propOrder = {
    "hashData",
    "hashAlgorithm",
    "friendlyName",
    "hashValue"
})
public class VerifyHashInfoRequestType {

    @XmlElement(name = "HashData", required = true)
    protected HashDataType hashData;
    @XmlElement(name = "HashAlgorithm", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String hashAlgorithm;
    @XmlElement(name = "FriendlyName")
    protected String friendlyName;
    @XmlElement(name = "HashValue", required = true)
    protected byte[] hashValue;

    /**
     * Gets the value of the hashData property.
     * 
     * @return
     *     possible object is
     *     {@link HashDataType }
     *     
     */
    public HashDataType getHashData() {
        return hashData;
    }

    /**
     * Sets the value of the hashData property.
     * 
     * @param value
     *     allowed object is
     *     {@link HashDataType }
     *     
     */
    public void setHashData(HashDataType value) {
        this.hashData = value;
    }

    /**
     * Gets the value of the hashAlgorithm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHashAlgorithm() {
        return hashAlgorithm;
    }

    /**
     * Sets the value of the hashAlgorithm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHashAlgorithm(String value) {
        this.hashAlgorithm = value;
    }

    /**
     * Gets the value of the friendlyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFriendlyName() {
        return friendlyName;
    }

    /**
     * Sets the value of the friendlyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFriendlyName(String value) {
        this.friendlyName = value;
    }

    /**
     * Gets the value of the hashValue property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getHashValue() {
        return hashValue;
    }

    /**
     * Sets the value of the hashValue property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setHashValue(byte[] value) {
        this.hashValue = ((byte[]) value);
    }

}
