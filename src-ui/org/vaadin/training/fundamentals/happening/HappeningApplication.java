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

package org.vaadin.training.fundamentals.happening;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.vaadin.training.fundamentals.happening.domain.entity.DomainUser;
import org.vaadin.training.fundamentals.happening.domain.impl.DomainUtils;
import org.vaadin.training.fundamentals.happening.ui.AppData;
import org.vaadin.training.fundamentals.happening.ui.ApplicationWithServices;
import org.vaadin.training.fundamentals.happening.ui.NavigationComponent;
import org.vaadin.training.fundamentals.happening.ui.NoAccessException;
import org.vaadin.training.fundamentals.happening.ui.NotAuthenticatedException;
import org.vaadin.training.fundamentals.happening.ui.ViewProvider;
import org.vaadin.training.fundamentals.happening.ui.Views;
import org.vaadin.training.fundamentals.happening.ui.ViewsImpl;
import org.vaadin.training.fundamentals.happening.ui.view.AddNewView;
import org.vaadin.training.fundamentals.happening.ui.view.EditHappeningView;
import org.vaadin.training.fundamentals.happening.ui.view.ListHappeningsView;
import org.vaadin.training.fundamentals.happening.ui.view.LoginView;
import org.vaadin.training.fundamentals.happening.ui.view.ShowHappeningView;
import org.vaadin.training.fundamentals.happening.ui.view.VaadinView;
import org.vaadin.training.fundamentals.happening.ui.viewimpl.EditHappeningViewImpl;
import org.vaadin.training.fundamentals.happening.ui.viewimpl.ListHappeningsViewImpl;
import org.vaadin.training.fundamentals.happening.ui.viewimpl.LoginViewImpl;
import org.vaadin.training.fundamentals.happening.ui.viewimpl.ShowHappeningViewImpl;

import com.vaadin.Application;
import com.vaadin.terminal.Terminal;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.*;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedEvent;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedListener;

public class HappeningApplication extends Application implements
        HttpServletRequestListener, ApplicationWithServices,
        FragmentChangedListener {
    private static final long serialVersionUID = 1L;
    private NavigationComponent navigation;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UriFragmentUtility uriFragmentUtility;

    @Override
    public void init() {
        setLogoutURL(getURL().toString() + "../logout.html");

        AppData appData = new AppData(this);
        getContext().addTransactionListener(appData);

        DomainUser user = DomainUtils.auth(getCookie());
        AppData.setCurrentUser(user);

        Window mainWindow = new Window(AppData.getTr(getLocale()).getString(
                "MainWindow.Caption"));
        setMainWindow(mainWindow);

        navigation = new NavigationComponent();
        registerViewProviders(navigation);

        uriFragmentUtility = new UriFragmentUtility();
        uriFragmentUtility.addListener(this);

        getMainWindow().setContent(navigation);
        navigation.setSizeFull();
        getMainWindow().addComponent(uriFragmentUtility);
        navigation.setCurrentView(LoginView.class, null);

        setTheme("happeningtheme");
    }

    @Override
    public void terminalError(final Terminal.ErrorEvent event) {
        event.getThrowable().printStackTrace();
        Throwable cause = event.getThrowable().getCause();
        if (cause instanceof RuntimeException && cause.getCause() != null) {
            Throwable innerCause = cause.getCause();
            if (innerCause instanceof NoAccessException) {
                getMainWindow().showNotification(
                        AppData.getTr(AppData.getLocale()).getString(
                                "Common.NoAccess"),
                        Window.Notification.TYPE_ERROR_MESSAGE);
            } else if (innerCause instanceof NotAuthenticatedException) {
                navigation.setCurrentView(LoginView.class, null);
            }
        } else {
            super.terminalError(event);
        }
    }

    void showLoggedInContent() {

    }

    private void registerViewProviders(NavigationComponent mainLayout) {
        Views views = new ViewsImpl();
        views.addProvider(ListHappeningsView.class, new ViewProvider() {
            public VaadinView<?> newView() {
                return new ListHappeningsViewImpl();
            }
        });
        views.addProvider(EditHappeningView.class, new ViewProvider() {
            public VaadinView<?> newView() {
                return new EditHappeningViewImpl();
            }
        });
        views.addProvider(AddNewView.class, new ViewProvider() {
            public VaadinView<?> newView() {
                return new EditHappeningViewImpl();
            }
        });
        views.addProvider(ShowHappeningView.class, new ViewProvider() {
            @Override
            public VaadinView<?> newView() {
                return new ShowHappeningViewImpl();
            }
        });
        views.addProvider(LoginView.class, new ViewProvider() {
            @Override
            public VaadinView<?> newView() {
                return new LoginViewImpl();
            }
        });
        mainLayout.setViews(views);
    }

    public void onRequestStart(HttpServletRequest request,
            HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public void onRequestEnd(HttpServletRequest request,
            HttpServletResponse response) {
        this.request = null;
        this.response = null;
    }

    @Override
    public String getCookie() {
        if (request != null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("userid".equals(cookie.getName())) {
                        return cookie.getValue();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void clearCookie() {
        if (response != null) {
            Cookie cookie = new Cookie("userid", "");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    @Override
    public void setCookie(String data) {
        if (response != null) {
            Cookie cookie = new Cookie("userid", data);
            cookie.setPath("/");
            cookie.setMaxAge(3600 * 24 * 30);
            response.addCookie(cookie);
        }
    }

    @Override
    public UriFragmentUtility getUriFragmentUtility() {
        return uriFragmentUtility;
    }

    /**
     * Client has changed the fragment in the browser.
     */
    @Override
    public void fragmentChanged(FragmentChangedEvent source) {
        String fragment = source.getUriFragmentUtility().getFragment();
        if (fragment.isEmpty()) {
            return;
        }
        Class<?> viewType;
        try {
            viewType = Class
                    .forName("org.vaadin.training.fundamentals.happening.ui.view."
                            + NavigationComponent.parseFragmentView(fragment));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Fragment " + fragment
                    + " is not of View.");
        }
        Map<String, String> params = NavigationComponent
                .parseFragmentParams(fragment);
        if (viewType != null) {
            navigation.setCurrentView(viewType, params);
        } else {
            throw new RuntimeException("Fragment " + fragment
                    + " is not of View.");
        }
    }
}
