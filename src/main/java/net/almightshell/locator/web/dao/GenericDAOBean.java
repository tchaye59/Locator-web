/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.almightshell.locator.web.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    public List<E> getAll() {
        String jpql = "SELECT e FROM " + this.entityClass.getSimpleName() + " e";
        Query query = this.em.createQuery(jpql);
        return query.getResultList();
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
