/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.almightshell.locator.web.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import net.almightshell.locator.web.api.Event;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

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

    E getOne(String field, String value);

    E getOne(String field, int value);

    List<E> getAll();

    List<E> getAll(String field, String value);

    List<E> getAll(String field, int value);

    public List<E> getAll(int first, int pageSize, Map<String, String> multiSortMeta, Map<String, Object> filters);

    public List<E> getAll(int first, int pageSize, String sortField, String sortOrder, Map<String, Object> filters);

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
