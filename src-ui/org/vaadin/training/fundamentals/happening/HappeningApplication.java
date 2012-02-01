package org.vaadin.training.fundamentals.happening;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.vaadin.training.fundamentals.happening.domain.AppData;
import org.vaadin.training.fundamentals.happening.domain.HasCookie;
import org.vaadin.training.fundamentals.happening.domain.entity.DomainUser;
import org.vaadin.training.fundamentals.happening.domain.impl.DomainUtils;
import org.vaadin.training.fundamentals.happening.ui.NavigationComponent;
import org.vaadin.training.fundamentals.happening.ui.VaadinView;
import org.vaadin.training.fundamentals.happening.ui.ViewProvider;
import org.vaadin.training.fundamentals.happening.ui.Views;
import org.vaadin.training.fundamentals.happening.ui.ViewsImpl;
import org.vaadin.training.fundamentals.happening.ui.edit.EditHappeningViewImpl;
import org.vaadin.training.fundamentals.happening.ui.edit.EditHappeningView;
import org.vaadin.training.fundamentals.happening.ui.list.ListHappeningsViewImpl;
import org.vaadin.training.fundamentals.happening.ui.list.ListHappeningsView;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.*;

public class HappeningApplication extends Application implements
        HttpServletRequestListener, HasCookie {
    private static final long serialVersionUID = 1L;
    private NavigationComponent navigation;
    private HttpServletRequest request;
    private HttpServletResponse response;

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
        mainWindow.setContent(navigation);
        navigation.setCurrentView(EditHappeningView.class, null);
    }
    
    private static void registerViewProviders(NavigationComponent mainLayout) {
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
}
