//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.01.18 at 07:53:38 AM CET 
//


package com.qpark.eip.core.spring.lockedoperation.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsDateTime;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XmlAdapterUtils;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.HashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * Contains the event to start, stop, block or unblock of an bus.app provided operation.
 * 
 * <p>Java class for OperationEventType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OperationEventType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="operationUUID" type="{http://www.ses.com/CommonTypes-1.0}UUIDType"/&gt;
 *         &lt;element name="event" type="{http://www.ses.com/Utility/AppControlling/AppControlling-1.0}OperationEventEnumType"/&gt;
 *         &lt;element name="operationTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperationEventType", propOrder = {
    "operationUUID",
    "event",
    "operationTime"
})
@Entity(name = "OperationEventType")
@Table(name = "OPERATIONEVENTTYPE")
@Inheritance(strategy = InheritanceType.JOINED)
public class OperationEventType
    implements Serializable, Equals, HashCode
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected OperationEventEnumType event;
    @XmlTransient
    protected Long hjid;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar operationTime;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String operationUUID;

    public boolean equals(Object object) {
        final EqualsStrategy strategy = JAXBEqualsStrategy.INSTANCE;
        return equals(null, null, object, strategy);
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if ((object == null)||(this.getClass()!= object.getClass())) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final OperationEventType that = ((OperationEventType) object);
        {
            String lhsOperationUUID;
            lhsOperationUUID = this.getOperationUUID();
            String rhsOperationUUID;
            rhsOperationUUID = that.getOperationUUID();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "operationUUID", lhsOperationUUID), LocatorUtils.property(thatLocator, "operationUUID", rhsOperationUUID), lhsOperationUUID, rhsOperationUUID)) {
                return false;
            }
        }
        {
            OperationEventEnumType lhsEvent;
            lhsEvent = this.getEvent();
            OperationEventEnumType rhsEvent;
            rhsEvent = that.getEvent();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "event", lhsEvent), LocatorUtils.property(thatLocator, "event", rhsEvent), lhsEvent, rhsEvent)) {
                return false;
            }
        }
        {
            XMLGregorianCalendar lhsOperationTime;
            lhsOperationTime = this.getOperationTime();
            XMLGregorianCalendar rhsOperationTime;
            rhsOperationTime = that.getOperationTime();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "operationTime", lhsOperationTime), LocatorUtils.property(thatLocator, "operationTime", rhsOperationTime), lhsOperationTime, rhsOperationTime)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the value of the event property.
     * 
     * @return
     *     possible object is
     *     {@link OperationEventEnumType }
     *     
     */
    @Basic
    @Column(name = "EVENT", length = 255)
    @Enumerated(EnumType.STRING)
    public OperationEventEnumType getEvent() {
        return event;
    }

    /**
     * 
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    @Id
    @Column(name = "HJID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getHjid() {
        return hjid;
    }

    /**
     * Gets the value of the operationTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    @Transient
    public XMLGregorianCalendar getOperationTime() {
        return operationTime;
    }

    @Basic
    @Column(name = "OPERATIONTIMEITEM")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getOperationTimeItem() {
        return XmlAdapterUtils.unmarshall(XMLGregorianCalendarAsDateTime.class, this.getOperationTime());
    }

    /**
     * Gets the value of the operationUUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "OPERATIONUUID", length = 36)
    public String getOperationUUID() {
        return operationUUID;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            String theOperationUUID;
            theOperationUUID = this.getOperationUUID();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "operationUUID", theOperationUUID), currentHashCode, theOperationUUID);
        }
        {
            OperationEventEnumType theEvent;
            theEvent = this.getEvent();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "event", theEvent), currentHashCode, theEvent);
        }
        {
            XMLGregorianCalendar theOperationTime;
            theOperationTime = this.getOperationTime();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "operationTime", theOperationTime), currentHashCode, theOperationTime);
        }
        return currentHashCode;
    }

    @Transient
    public boolean isSetEvent() {
        return (this.event!= null);
    }

    @Transient
    public boolean isSetOperationTime() {
        return (this.operationTime!= null);
    }

    @Transient
    public boolean isSetOperationUUID() {
        return (this.operationUUID!= null);
    }

    /**
     * Sets the value of the event property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationEventEnumType }
     *     
     */
    public void setEvent(OperationEventEnumType value) {
        this.event = value;
    }

    /**
     * 
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setHjid(Long value) {
        this.hjid = value;
    }

    /**
     * Sets the value of the operationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOperationTime(XMLGregorianCalendar value) {
        this.operationTime = value;
    }

    public void setOperationTimeItem(Date target) {
        setOperationTime(XmlAdapterUtils.marshall(XMLGregorianCalendarAsDateTime.class, target));
    }

    /**
     * Sets the value of the operationUUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationUUID(String value) {
        this.operationUUID = value;
    }

}
