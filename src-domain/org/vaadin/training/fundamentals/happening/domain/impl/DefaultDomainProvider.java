package org.vaadin.training.fundamentals.happening.domain.impl;

import org.vaadin.training.fundamentals.happening.domain.Domain;
import org.vaadin.training.fundamentals.happening.domain.DomainProvider;

public class DefaultDomainProvider implements DomainProvider {

    public Domain newDomain() {
        return new DefaultDomain();
    }

}
