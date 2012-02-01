package org.vaadin.training.fundamentals.happening;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.vaadin.training.fundamentals.happening.domain.Domains;
import org.vaadin.training.fundamentals.happening.domain.impl.DefaultDomainProvider;

public class HappeningContextListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent sce) {
    }

    public void contextInitialized(ServletContextEvent sce) {
        Domains.registerDefaultDomainProvider(new DefaultDomainProvider());
    }

}
