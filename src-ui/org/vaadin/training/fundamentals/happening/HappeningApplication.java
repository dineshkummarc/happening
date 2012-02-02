package org.vaadin.training.fundamentals.happening;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.vaadin.training.fundamentals.happening.domain.entity.DomainUser;
import org.vaadin.training.fundamentals.happening.domain.impl.DomainUtils;
import org.vaadin.training.fundamentals.happening.ui.AppData;
import org.vaadin.training.fundamentals.happening.ui.ApplicationWithServices;
import org.vaadin.training.fundamentals.happening.ui.NavigationComponent;
import org.vaadin.training.fundamentals.happening.ui.VaadinView;
import org.vaadin.training.fundamentals.happening.ui.ViewProvider;
import org.vaadin.training.fundamentals.happening.ui.Views;
import org.vaadin.training.fundamentals.happening.ui.ViewsImpl;
import org.vaadin.training.fundamentals.happening.ui.edit.AddNewView;
import org.vaadin.training.fundamentals.happening.ui.edit.EditHappeningViewImpl;
import org.vaadin.training.fundamentals.happening.ui.edit.EditHappeningView;
import org.vaadin.training.fundamentals.happening.ui.list.ListHappeningsViewImpl;
import org.vaadin.training.fundamentals.happening.ui.list.ListHappeningsView;

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
    private Map<String, Class<?>> fragmentViewMap = new HashMap<String, Class<?>>();

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
        fragmentViewMap.put(EditHappeningView.class.getSimpleName(), EditHappeningView.class);
        fragmentViewMap.put(ListHappeningsView.class.getSimpleName(), ListHappeningsView.class);
        fragmentViewMap.put(AddNewView.class.getSimpleName(), AddNewView.class);
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
                    // Figure out why Vaadin sets empty string after login
                    return;
            }
            Class<?> viewType = fragmentViewMap.get(NavigationComponent.parseFragmentView(fragment));
            Map<String, String> params = NavigationComponent.parseFragmentParams(fragment);
            if (viewType != null) {
                navigation.setCurrentView(viewType, params);
            } else {
                throw new RuntimeException("Fragment " + fragment + " is not of View.");
            }
    }    
}
