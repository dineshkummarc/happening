package org.vaadin.training.fundamentals.happening.domain.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.jpa.JpaHelper;
import org.vaadin.training.fundamentals.happening.domain.Domain;
import org.vaadin.training.fundamentals.happening.domain.entity.AbstractEntity;

/**
 * This class should not be extended.
 * 
 * @author Johannes
 * 
 */
class DefaultDomain implements Domain {

    EntityManagerFactory emf;
    EntityManager em;

    public DefaultDomain() {
        emf = Persistence.createEntityManagerFactory("h2");
    }

    @Override
    public <A extends AbstractEntity> A find(Class<A> clazz, Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(clazz, id);
        } finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A extends AbstractEntity> List<A> list(Class<A> clazz) {
        EntityManager em = getEntityManager();
        try {
            ExpressionBuilder builder = new ExpressionBuilder();
            JpaEntityManager jpaEm = JpaHelper.getEntityManager(em);
            Query query = jpaEm.createQuery(builder, clazz);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public <A extends AbstractEntity> List<A> list(Class<A> clazz, String queryStr,
            Map<String, Object> parameters) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<A> query = em.createQuery(queryStr, clazz);
            if (parameters != null) {
                for (String key : parameters.keySet()) {
                    query.setParameter(key, parameters.get(key));
                }
            }
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public <A extends AbstractEntity> List<A> list(String queryStr,
            Map<String, Object> parameters, int max) {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A extends AbstractEntity> A find(String queryStr, Map<String, Object> parameters) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery(queryStr);
            if (parameters != null) {
                for (String key : parameters.keySet()) {
                    query.setParameter(key, parameters.get(key));
                }
            }

            return (A) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public <A extends AbstractEntity> A store(A pojo) {
        EntityManager em = getEntityManager();
        A stored = pojo;
        try {
            em.getTransaction().begin();
            if (pojo.getId() != null) {
                stored = em.merge(pojo);
            } else {
                em.persist(pojo);
            }            
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return stored;
    }

    @Override
    public <A extends AbstractEntity> void storeAll(Collection<A> pojos) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(AbstractEntity pojo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    @Override
    public <A extends AbstractEntity> void deleteAll(Collection<A> pojos) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() {
        if (em != null && em.isOpen()) {
            em.clear();
            em.close();
        }
    }

    @Override
    public long count(String queryStr, Map<String, Object> parameters) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    private EntityManager getEntityManager() {
        if ((em == null || !em.isOpen()) && emf != null) {
            em = emf.createEntityManager();
        }

        return em;
    }
}
