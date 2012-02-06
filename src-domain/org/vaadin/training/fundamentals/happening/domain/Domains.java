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

/**
 * Allows {@link DomainProvider} objects to be registerd to be used for creating
 * new instances of {@link Domain} objects.
 * 
 * @author Johannes
 * 
 */
public class Domains {
    private Domains() {
    };

    private static final Map<String, DomainProvider> providers = new ConcurrentHashMap<String, DomainProvider>();

    public static final String DEFAULT_DOMAIN_PROVIDER_NAME = "default-provider";

    /**
     * Registers a new provider to be the default provider. e.g. the provider to
     * be used when {@link #newInstance()} is called.
     * 
     * @param p
     */
    public static void registerDefaultDomainProvider(DomainProvider p) {
        registerProvider(DEFAULT_DOMAIN_PROVIDER_NAME, p);
    }

    public static void registerProvider(String name, DomainProvider p) {
        providers.put(name, p);
    }

    /**
     * Creates a new instance of {@link Domain} from the default provider
     * 
     * @return
     */
    public static Domain newInstance() {
        return newInstance(DEFAULT_DOMAIN_PROVIDER_NAME);
    }

    public static Domain newInstance(String name) {
        DomainProvider p = providers.get(name);
        if (p == null) {
            throw new IllegalArgumentException(
                    "No provider registered with name: " + name);
        }
        return p.newDomain();
    }
}
