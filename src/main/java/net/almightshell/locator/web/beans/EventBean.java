/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.almightshell.locator.web.beans;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.almightshell.locator.web.api.Event;
import net.almightshell.locator.web.dao.EventDAOBeanLocal;

/**
 *
 * @author Shell
 */
@ManagedBean
@ViewScoped
public class EventBean implements Serializable {

    @EJB
    private EventDAOBeanLocal serviceDAO;

    private List<Event> selecteds = new LinkedList();

    private Event event;

    private int id;

    @PostConstruct
    public void init() {
        event = new Event();
    }

    public void initEvent() {
        if (id != 0) {
            this.event = serviceDAO.getOne(id);
        }
    }

    public String save() {
        serviceDAO.updateOne(event);
        return "list?faces-redirect=true";
    }

    public String delete() {
        for (Event selected : selecteds) {
            serviceDAO.deleteOne(selected);
        }
        return "";
    }

    public EventDAOBeanLocal getServiceDAO() {
        return serviceDAO;
    }

    public List<Event> getAll() {
        return serviceDAO.getAll();
    }

    public List<Event> getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<Event> selecteds) {
        this.selecteds = selecteds;
    }

    public Event getEvent() {
        return event;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
