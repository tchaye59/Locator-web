/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.almightshell.locator.web.dao;

import javax.ejb.Local;
import net.almightshell.locator.web.api.PowerCenter;

/**
 *
 * @author Shell
 */
@Local
public interface PowerCenterDAOBeanLocal extends GenericDAOBeanLocal<PowerCenter, Integer> {

}
