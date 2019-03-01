/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.almightshell.locator.web.dao;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Shell
 * @param <E> L'entité qui implemente le GéneriqueDaoBean
 * @param <ID> La clé primaire de l'entité
 */
public interface GenericDAOBeanLocal<E extends Serializable, ID> {

    /**
     * Persiste l'objet passé en paramètre.
     *
     * @param e L'objet à persister.
     */
    void addOne(E e);

    E updateOne(E e);

    void deleteOne(E e);

    void deleteOne(ID id);

    void deleteAll();

    E getOne(ID id);

    List<E> getAll();

    Long count();

    /**
     * Vérifie si l'objet entité dont l'identifiant est passé en paramètre
     * existe dans l'unité de persistence.
     *
     * @param id L'identifiant de l'objet entité à vérifier.
     * @return <code>true</code> si l'objet existe.
     */
    boolean exists(ID id);
}
