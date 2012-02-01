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
