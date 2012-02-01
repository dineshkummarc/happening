package org.vaadin.training.fundamentals.happening.ui;

import java.util.Map;

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

    public VaadinView<?> setCurrentView(Class<?> type, Map<String, Object> params) {
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

        } catch (final Exception e) {
            throw new RuntimeException("View instantiation failed!", e);
        }

        return activeView;
    }

    public void setViews(Views views) {
        this.views = views;
    }
}
