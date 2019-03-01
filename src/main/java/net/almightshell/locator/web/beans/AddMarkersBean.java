/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.almightshell.locator.web.beans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import net.almightshell.locator.web.api.Event;
import net.almightshell.locator.web.api.PowerCenter;
import net.almightshell.locator.web.dao.EventDAOBeanLocal;
import net.almightshell.locator.web.dao.PowerCenterDAOBeanLocal;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.Circle;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author Shell
 */
@ManagedBean
@ViewScoped
public class AddMarkersBean implements Serializable {

    @EJB
    private PowerCenterDAOBeanLocal pcserviceDAO;

    @EJB
    private EventDAOBeanLocal eventServiceDAO;

    private MapModel emptyModel;
    private PowerCenter powerCenter;
    private Event event;

    private double lat;
    private double lon;

    @PostConstruct
    public void init() {
        emptyModel = new DefaultMapModel();
        addMarkers();
    }

    public void locateEvent(int eventID) {
        Event e = eventServiceDAO.getOne(eventID);

        lon = e.getLon();
        lat = e.getLat();

        Circle c = new Circle(new LatLng(e.getLat(), e.getLon()), 300);
        c.setStrokeColor("#d93c3c");
        c.setFillColor("#d93c3c");
        c.setStrokeOpacity(0.7);
        c.setFillOpacity(0.7);
        c.setData(e);

        emptyModel.addOverlay(c);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Searching...", ""));
    }

    public void addMarkers() {
        for (PowerCenter pc : pcserviceDAO.getAll()) {
            lon = pc.getLon();
            lat = pc.getLat();
            Marker marker = new Marker(new LatLng(pc.getLat(), pc.getLon()), pc.getRegion() + ":" + pc.getName(), pc);
            emptyModel.addOverlay(marker);
        }
    }

    public void onMarkerSelect(OverlaySelectEvent event) {

        if (event.getOverlay() == null) {
            powerCenter = null;
            this.event = null;
            return;
        }

        if (event.getOverlay() instanceof Marker) {
            Marker marker = (Marker) event.getOverlay();
            powerCenter = (PowerCenter) marker.getData();
            this.event = null;
        }

        if (event.getOverlay() instanceof Circle) {
            Circle circle = (Circle) event.getOverlay();
            this.event = (Event) circle.getData();
            this.powerCenter = null;
        }

    }

    public PowerCenterDAOBeanLocal getPcserviceDAO() {
        return pcserviceDAO;
    }

    public MapModel getEmptyModel() {
        return emptyModel;
    }

    public double getLat() {
        return lat;
    }

    public PowerCenter getPowerCenter() {
        return powerCenter;
    }

    public double getLon() {
        return lon;
    }

    public Event getEvent() {
        return event;
    }

}
