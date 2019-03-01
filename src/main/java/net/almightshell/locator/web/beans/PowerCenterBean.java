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
import net.almightshell.locator.web.api.PowerCenter;
import net.almightshell.locator.web.dao.PowerCenterDAOBeanLocal;

/**
 *
 * @author Shell
 */
@ManagedBean
@ViewScoped
public class PowerCenterBean implements Serializable {

    @EJB
    private PowerCenterDAOBeanLocal serviceDAO;

    private List<PowerCenter> selecteds = new LinkedList();

    private PowerCenter powerCenter;

    private int id;
    
    

    @PostConstruct
    public void init() {
        powerCenter = new PowerCenter();
    }

    public void initPowerCenter() {
        if (id != 0) {
            this.powerCenter = serviceDAO.getOne(id);
        }
    }

    public String save() {
        serviceDAO.updateOne(powerCenter);
        return "list?faces-redirect=true";
    }

    public String delete() {
        for (PowerCenter selected : selecteds) {
            serviceDAO.deleteOne(selected);
        }
        return "";
    }

    public PowerCenterDAOBeanLocal getServiceDAO() {
        return serviceDAO;
    }

    public List<PowerCenter> getAll() {
        return serviceDAO.getAll();
    }

    public List<PowerCenter> getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<PowerCenter> selecteds) {
        this.selecteds = selecteds;
    }

    public PowerCenter getPowerCenter() {
        return powerCenter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
