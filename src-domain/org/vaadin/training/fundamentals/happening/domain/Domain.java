/* 
 * Copyright 2012 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.vaadin.training.fundamentals.happening.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.vaadin.training.fundamentals.happening.domain.entity.AbstractEntity;

/**
 * Facade for implementations providing CRUD operations for
 * {@link AbstractEntity} entities.
 * 
 * @author Johannes
 * 
 */
public interface Domain {

    /**
     * Find a single entity of type by id.
     * 
     * @param clazz
     * @param id
     * @return
     */
    public <A extends AbstractEntity> A find(Class<A> clazz, Long id);

    /**
     * List all entities of type
     * 
     * @param clazz
     * @return
     */
    public <A extends AbstractEntity> List<A> list(Class<A> clazz);

    /**
     * List entities of type by a custom query and query parameters.
     * 
     * @param clazz
     * @param queryStr
     * @param parameters
     * @return
     */
    public <A extends AbstractEntity> List<A> list(Class<A> clazz,
            String queryStr, Map<String, Object> parameters);

    /**
     * List entities of type by a custom query and query parameters, but limit
     * the number of results to the given maximum.
     * 
     * @param queryStr
     * @param parameters
     * @param max
     * @return
     */
    public <A extends AbstractEntity> List<A> list(String queryStr,
            Map<String, Object> parameters, int max);

    /**
     * Find a single entity of type using a custom query and query parameters.s
     * 
     * @param queryStr
     * @param parameters
     * @return
     */
    public <A extends AbstractEntity> A find(String queryStr,
            Map<String, Object> parameters);

    /**
     * Store (create or update) a given entity.
     * 
     * @param pojo
     * @return
     */
    public <A extends AbstractEntity> A store(A pojo);

    /**
     * Store (create or update) a collection of entities.
     * 
     * @param pojos
     */
    public <A extends AbstractEntity> void storeAll(Collection<A> pojos);

    /**
     * Delete a single entity
     * 
     * @param pojo
     */
    public void delete(AbstractEntity pojo);

    /**
     * Delete a collection of entities.
     * 
     * @param pojos
     */
    public <A extends AbstractEntity> void deleteAll(Collection<A> pojos);

    /**
     * Close the storage.
     */
    public void close();

    /**
     * Count the number of results for a given query
     * 
     * @param queryStr
     * @param parameters
     * @return
     */
    public long count(String queryStr, Map<String, Object> parameters);

}