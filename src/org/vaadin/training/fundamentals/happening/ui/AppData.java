package org.vaadin.training.fundamentals.happening.ui;

import org.vaadin.training.fundamentals.happening.domain.Domain;
import org.vaadin.training.fundamentals.happening.domain.Domains;
import org.vaadin.training.fundamentals.happening.domain.entity.DomainUser;

import com.vaadin.Application;
import com.vaadin.service.ApplicationContext.TransactionListener;
import com.vaadin.ui.UriFragmentUtility;

public class AppData implements TransactionListener {
    
    private static final long serialVersionUID = 1L;

    private static final ThreadLocal<AppData> instance = new ThreadLocal<AppData>();
    
    private Application app;
    private DomainUser user;
    private Domain domain;
    
    public AppData(Application app) {
        this.app = app;
        instance.set(this);
    }

    @Override
    public void transactionStart(Application application, Object transactionData) {
        if (application == app) {
            instance.set(this);
        }
    }

    @Override
    public void transactionEnd(Application application, Object transactionData) {
        if (app == application) {
            instance.set(null);
        }
    }
    
    public static Domain getDomain() {
        if (instance.get().domain == null) {
            Domain domain = Domains.newInstance();
            instance.get().domain = domain;
        }
        return instance.get().domain;
    }
    
    public static void setCurrentUser(DomainUser user) {
        instance.get().user = user;
    }
    
    public static DomainUser getCurrentUser() {
        return instance.get().user;
    }
    
    public static void setUserCookie() {
        Application app = instance.get().app;
        if (app instanceof ApplicationWithServices) {
            ((ApplicationWithServices)app).setCookie(instance.get().user.getHash());
        }
    }
    
    public static void clearUserCookie() {
        Application app = instance.get().app;
        if (app instanceof ApplicationWithServices) {
            ((ApplicationWithServices)app).clearCookie();
        }        
    }
    
    public static UriFragmentUtility getUriFragmentUtility() {
        Application app = instance.get().app;
        if (app instanceof ApplicationWithServices) {
            return ((ApplicationWithServices)app).getUriFragmentUtility();
        }
        return null;
    }
}
