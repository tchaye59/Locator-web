/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.almightshell.locator.web.dao;

import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import net.almightshell.locator.web.api.Event;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Shell
 */
@Stateless
public class EventDAOBean extends GenericDAOBean<Event, Integer> implements EventDAOBeanLocal {

    public EventDAOBean() {
        super(Event.class);
    }

}
