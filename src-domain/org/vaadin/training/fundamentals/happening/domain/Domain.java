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