//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.06.16 at 02:43:37 PM CEST 
//


package org.opengroup.xsd.archimate._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 ObjectType which requires a Name and an ID.
 *             
 * 
 * <p>Java class for NamedReferenceableType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NamedReferenceableType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.opengroup.org/xsd/archimate/3.0/}ReferenceableType">
 *       &lt;sequence>
 *         &lt;group ref="{http://www.opengroup.org/xsd/archimate/3.0/}NameGroup" maxOccurs="unbounded"/>
 *         &lt;group ref="{http://www.opengroup.org/xsd/archimate/3.0/}DocumentationGroup"/>
 *         &lt;group ref="{http://www.opengroup.org/xsd/archimate/3.0/}grp.any"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.opengroup.org/xsd/archimate/3.0/}IdentifierGroup"/>
 *       &lt;anyAttribute namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NamedReferenceableType")
@XmlSeeAlso({
    ModelType.class,
    PropertyDefinitionType.class
})
public abstract class NamedReferenceableType
    extends ReferenceableType
{


}
