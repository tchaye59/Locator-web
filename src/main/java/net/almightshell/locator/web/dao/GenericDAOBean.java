/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.almightshell.locator.web.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Shell
 */
public abstract class GenericDAOBean<E extends Serializable, ID> implements GenericDAOBeanLocal<E, ID> {

    /**
     * Gerer les transactions liés aux methodes
     */
    @PersistenceContext
    protected EntityManager em;

    private final Class<E> entityClass;

    public GenericDAOBean(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void addOne(E e) {
        this.em.persist(e);
    }

    @Override
    public E updateOne(E e) {
        return this.em.merge(e);
    }

    @Override
    public void deleteOne(E e) {
        this.em.remove(this.em.merge(e));
    }

    @Override
    public void deleteOne(ID id) {
        this.em.remove(this.getOne(id));
    }

    @Override
    public void deleteAll() {
        String jpql = "DELETE FROM " + this.entityClass.getSimpleName();
        Query query = this.em.createQuery(jpql);
        query.executeUpdate();
    }

    @Override
    public E getOne(ID id) {
        return this.em.find(this.entityClass, id);
    }

    @Override
    public E getOne(String field, String value) {
        try {
            return getAll(field, value).get(0);
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public E getOne(String field, int value) {
        try {
            return getAll(field, value).get(0);
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public List<E> getAll() {
        String jpql = "SELECT e FROM " + this.entityClass.getSimpleName() + " e";
        Query query = this.em.createQuery(jpql);
        return query.getResultList();
    }

    @Override
    public List<E> getAll(String field, String value) {
        String jpql = "SELECT e FROM " + this.entityClass.getSimpleName() + " e "
                + "WHERE e." + field + "=:field";
        Query query = this.em.createQuery(jpql);
        query.setParameter("field", value);
        return query.getResultList();
    }

    @Override
    public List<E> getAll(String field, int value) {
        String jpql = "SELECT e FROM " + this.entityClass.getSimpleName() + " e "
                + "WHERE e." + field + "=:field";
        Query query = this.em.createQuery(jpql);
        query.setParameter("field", value);
        return query.getResultList();
    }

    @Override
    public List<E> getAll(int first, int pageSize, Map<String, String> multiSortMeta, Map<String, Object> filters) {
        String jpql = "SELECT e FROM " + this.entityClass.getSimpleName() + " e "
                + "WHERE 1=1 ";

        if (filters != null) {
            for (Map.Entry<String, Object> v : filters.entrySet()) {
                jpql += " AND UPPER(e." + v.getKey() + ") LIKE UPPER(:" + v.getKey() + ") ";
            }
        }

        if (multiSortMeta != null) {
            String chunk = "";
            int i = 0;
            for (Map.Entry<String, String> param : multiSortMeta.entrySet()) {
                if (i >= 1) {
                    chunk += ", ";
                }
                chunk += "e." + param.getKey() + " ";

                if (param.getValue().equalsIgnoreCase(SortOrder.ASCENDING.toString())) {
                    chunk += "ASC";
                }
                if (param.getValue().equalsIgnoreCase(SortOrder.DESCENDING.toString())) {
                    chunk += "DESC";
                }

                i++;
            }

            if (!chunk.isEmpty()) {
                jpql += " ORDER BY " + chunk;
            }
        }

        Query query = this.em.createQuery(jpql);

        if (filters != null) {
            for (Map.Entry<String, Object> v : filters.entrySet()) {
                query.setParameter(v.getKey(), "%" + v.getValue() + "%");
            }
        }

        return query.setFirstResult(first)
                .setMaxResults(pageSize).getResultList();
    }

    @Override
    public List<E> getAll(int first, int pageSize, String sortField, String sortOrder, Map<String, Object> filters) {
        Map<String, String> metas = new HashMap<>();
        if (sortField != null) {
            metas.put(sortField, sortOrder);
        }

        return getAll(first, pageSize, metas, filters);
    }

    @Override
    public Long count() {
        String jpql = "SELECT COUNT(e) FROM " + this.entityClass.getSimpleName() + " e";
        Query query = this.em.createQuery(jpql);
        return (Long) query.getSingleResult();
    }

    @Override
    public boolean exists(ID id) {
        return this.getOne(id) != null;
    }
}
