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
import org.vaadin.training.fundamentals.happening.ui.ViewProvider;
import org.vaadin.training.fundamentals.happening.ui.Views;
import org.vaadin.training.fundamentals.happening.ui.ViewsImpl;
import org.vaadin.training.fundamentals.happening.ui.view.AddNewView;
import org.vaadin.training.fundamentals.happening.ui.view.EditHappeningView;
import org.vaadin.training.fundamentals.happening.ui.view.ListHappeningsView;
import org.vaadin.training.fundamentals.happening.ui.view.ShowHappeningView;
import org.vaadin.training.fundamentals.happening.ui.view.VaadinView;
import org.vaadin.training.fundamentals.happening.ui.viewimpl.EditHappeningViewImpl;
import org.vaadin.training.fundamentals.happening.ui.viewimpl.ListHappeningsViewImpl;
import org.vaadin.training.fundamentals.happening.ui.viewimpl.ShowHappeningViewImpl;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.*;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedEvent;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedListener;

public class HappeningApplication extends Application implements
        HttpServletRequestListener, ApplicationWithServices, FragmentChangedListener {
    private static final long serialVersionUID = 1L;
    private NavigationComponent navigation;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UriFragmentUtility uriFragmentUtility;

    @Override
    public void init() {
        AppData appData = new AppData(this);
        getContext().addTransactionListener(appData);
        
        DomainUser user = DomainUtils.auth(getCookie());
        AppData.setCurrentUser(user);
        
        Window mainWindow = new Window("Happening Application");
        setMainWindow(mainWindow);
        navigation = new NavigationComponent();
        registerViewProviders(navigation);
        uriFragmentUtility = new UriFragmentUtility();
        uriFragmentUtility.addListener(this);
        mainWindow.setContent(navigation);
        navigation.setSizeFull();
        mainWindow.addComponent(uriFragmentUtility);
        navigation.setCurrentView(EditHappeningView.class, null);
        setTheme("happeningtheme");
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
            cookie.setMaxAge(3600*24*30);
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
                viewType = Class.forName("org.vaadin.training.fundamentals.happening.ui.view." + NavigationComponent.parseFragmentView(fragment));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("Fragment " + fragment + " is not of View.");
            }
            Map<String, String> params = NavigationComponent.parseFragmentParams(fragment);
            if (viewType != null) {
                navigation.setCurrentView(viewType, params);
            } else {
                throw new RuntimeException("Fragment " + fragment + " is not of View.");
            }
    }    
}
