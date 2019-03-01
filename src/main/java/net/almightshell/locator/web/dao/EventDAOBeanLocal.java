/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.almightshell.locator.web.dao;

import javax.ejb.Local;
import net.almightshell.locator.web.api.Event;

/**
 *
 * @author Shell
 */
@Local
public interface EventDAOBeanLocal extends GenericDAOBeanLocal<Event, Integer> {

}
