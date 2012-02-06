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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Domains {
    private Domains() { };
    
    private static final Map<String, DomainProvider> providers = new ConcurrentHashMap<String, DomainProvider>();
    
    public static final String DEFAULT_DOMAIN_PROVIDER_NAME = "default-provider";
    
    public static void registerDefaultDomainProvider(DomainProvider p) {
        registerProvider(DEFAULT_DOMAIN_PROVIDER_NAME, p);
    }
    
    public static void registerProvider(String name, DomainProvider p) {
        providers.put(name, p);
    }
    
    public static Domain newInstance() {
        return newInstance(DEFAULT_DOMAIN_PROVIDER_NAME);
    }
    
    public static Domain newInstance(String name) {
        DomainProvider p = providers.get(name);
        if (p == null) {
            throw new IllegalArgumentException("No provider registered with name: " + name);
        }
        return p.newDomain();
    }
}
