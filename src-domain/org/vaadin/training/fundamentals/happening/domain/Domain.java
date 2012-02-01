package org.vaadin.training.fundamentals.happening.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.vaadin.training.fundamentals.happening.domain.entity.AbstractEntity;

public interface Domain {
   
    public <A extends AbstractEntity> A find(Class<A> clazz, Long id);

    public <A extends AbstractEntity> List<A> list(Class<A> clazz);

    public <A extends AbstractEntity> List<A> list(Class<A> clazz, String queryStr,
            Map<String, Object> parameters);

    public <A extends AbstractEntity> List<A> list(String queryStr,
            Map<String, Object> parameters, int max);

    public <A extends AbstractEntity> A find(String queryStr,
            Map<String, Object> parameters);

    public <A extends AbstractEntity> A store(A pojo);

    public <A extends AbstractEntity> void storeAll(Collection<A> pojos);

    public void delete(AbstractEntity pojo);

    public <A extends AbstractEntity> void deleteAll(Collection<A> pojos);

    public void close();

    public long count(String queryStr, Map<String, Object> parameters);
    
}