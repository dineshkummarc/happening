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

package org.vaadin.training.fundamentals.happening.ui;

import java.util.Locale;
import java.util.ResourceBundle;

import org.vaadin.training.fundamentals.happening.domain.Domain;
import org.vaadin.training.fundamentals.happening.domain.Domains;
import org.vaadin.training.fundamentals.happening.domain.entity.DomainUser;

import com.vaadin.Application;
import com.vaadin.service.ApplicationContext.TransactionListener;
import com.vaadin.ui.UriFragmentUtility;

/**
 * 
 * @author Johannes
 * 
 */
public class AppData implements TransactionListener {

    private static final long serialVersionUID = 1L;

    private static final ThreadLocal<AppData> instance = new ThreadLocal<AppData>();

    private Application app;
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

    /**
     * Return the ResourceBundle for a given locale. If no locale is defined
     * resources for Locale.ENGLISH is returned.
     * 
     * @param locale
     * @return
     */
    public static ResourceBundle getTr(Locale locale) {
        if (locale == null) {
            locale = Locale.ENGLISH;
        }
        return ResourceBundle.getBundle("CaptionsBundle", locale);
    }

    public static Locale getLocale() {
        return instance.get().app.getLocale();
    }

    /**
     * Get the {@link Domain} associated for the AppData.
     * 
     * @return
     */
    public static Domain getDomain() {
        if (instance.get().domain == null) {
            Domain domain = Domains.newInstance();
            instance.get().domain = domain;
        }
        return instance.get().domain;
    }

    /**
     * Sets the current user. Not the same as Application.setUser.
     * 
     * @param user
     */
    public static void setCurrentUser(DomainUser user) {
        instance.get().app.setUser(user);
    }

    /**
     * Gets the current user.
     * 
     * @return
     */
    public static DomainUser getCurrentUser() {
        return (DomainUser) instance.get().app.getUser();
    }

    /**
     * Sets the cookie information to be stored for the current user
     */
    public static void setUserCookie() {
        Application app = instance.get().app;
        if (app instanceof ApplicationWithServices) {
            ((ApplicationWithServices) app).setCookie(((DomainUser) app
                    .getUser()).getHash());
        }
    }

    /**
     * Removes the user cookie
     */
    public static void clearUserCookie() {
        Application app = instance.get().app;
        if (app instanceof ApplicationWithServices) {
            ((ApplicationWithServices) app).clearCookie();
        }
    }

    /**
     * Gets the UriFragmentUtility added to the main window
     * 
     * @return
     */
    public static UriFragmentUtility getUriFragmentUtility() {
        Application app = instance.get().app;
        if (app instanceof ApplicationWithServices) {
            return ((ApplicationWithServices) app).getUriFragmentUtility();
        }
        return null;
    }
}
