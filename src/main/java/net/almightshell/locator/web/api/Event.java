/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.almightshell.locator.web.api;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author Shell
 */
@Entity
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "event_id")
    String eventId;

    @Column(name = "region")
    String region;

    @Column(name = "name")
    String name;

    @Column(name = "latitude")
    String latitude;

    @Column(name = "longitude")
    String longitude;

    @Column(name = "lat")
    double lat;

    @Column(name = "lon")
    double lon;

    @Column(name = "event_time")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date eventTime;

    @Column(name = "scada_category")
    String scadaCategory;

    @Column(name = "priority_code")
    int priorityCode;

    @Column(name = "substation")
    String substation;

    @Column(name = "device_type")
    String deviceType;

    @Column(name = "device")
    String device;

    @Column(name = "event_message")
    String event_message;

    @Column(name = "tms")
    String tms;

    @Column(name = "aor")
    String aor;

    public Event() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public String getScadaCategory() {
        return scadaCategory;
    }

    public void setScadaCategory(String scadaCategory) {
        this.scadaCategory = scadaCategory;
    }

    public int getPriorityCode() {
        return priorityCode;
    }

    public void setPriorityCode(int priorityCode) {
        this.priorityCode = priorityCode;
    }

    public String getSubstation() {
        return substation;
    }

    public void setSubstation(String substation) {
        this.substation = substation;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getEvent_message() {
        return event_message;
    }

    public void setEvent_message(String event_message) {
        this.event_message = event_message;
    }

    public String getTms() {
        return tms;
    }

    public void setTms(String tms) {
        this.tms = tms;
    }

    public String getAor() {
        return aor;
    }

    public void setAor(String aor) {
        this.aor = aor;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Event other = (Event) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", region=" + region + ", name=" + name + ", latitude=" + latitude + ", longitude=" + longitude + ", lat=" + lat + ", lon=" + lon + ", eventTime=" + eventTime + ", scadaCategory=" + scadaCategory + ", priorityCode=" + priorityCode + ", substation=" + substation + ", deviceType=" + deviceType + ", device=" + device + ", event_message=" + event_message + '}';
    }

}
