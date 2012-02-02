package org.vaadin.training.fundamentals.happening.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class NavigationComponent extends VerticalLayout implements Navigation {

    private static final long serialVersionUID = 1L;

    private VaadinView<?> activeView;

    private Views views;

    public NavigationComponent() {
        buildLayout();
    }

    private void buildLayout() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.setHeight("50px");
        Label appName = new Label("Happening Application Header");
        header.addComponent(appName);
        addComponent(header);
    }

    public VaadinView<?> setCurrentView(Class<?> type, Map<String, String> params) {
        if (activeView != null && activeView.getClass().equals(type)) {
            // do nothing, since requested view already active
            return activeView;
        }

        try {
            // Create new view
            VaadinView<?> newView = views.newInstance(type);

            // Initialize selected view (once)
            newView.init(this, params);

            Component newContent = newView.getViewContent();

            // Remove current view if one exists
            if (activeView != null) {
                removeComponent(activeView.getViewContent());
            }

            activeView = newView;

            // Add new view
            addComponent(newContent);
            setExpandRatio(newContent, 1.0f);
            
            AppData.getUriFragmentUtility().setFragment(type.getSimpleName() + toFragmentParams(params));

        } catch (final Exception e) {
            throw new RuntimeException("View instantiation failed!", e);
        }

        return activeView;
    }

    public void setViews(Views views) {
        this.views = views;
    }
    
    public static String parseFragmentView(String fragment) {
        String[] s = fragment.split("/");
        if (s != null && s.length > 0) {
            return s[0];
        }
        return null;
    }
    
    public static Map<String, String> parseFragmentParams(String fragment) {
        String[] s = fragment.split("/");
        if (s != null && s.length > 1 && (s.length % 2) == 1) {
            HashMap<String, String> params = new HashMap<String, String>();
            for (int i = 1; i < s.length; i+=2) {
                params.put(s[i], s[i + 1]);
            }
            return params;
        }
        return null;
        
    }
    
    static String toFragmentParams(Map<String, String> params) {
        if (params == null) {
            return "";
        }
        String result = "/";
        for (Entry<String, String> entry : params.entrySet()) {
            result += entry.getKey() + "/" + entry.getValue();
        }
        return result;
    }
}
