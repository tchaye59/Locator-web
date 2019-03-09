/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.almightshell.locator.web.dao;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import net.almightshell.locator.web.api.Event;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Shell
 */
@Local
public interface EventDAOBeanLocal extends GenericDAOBeanLocal<Event, Integer> {


}
