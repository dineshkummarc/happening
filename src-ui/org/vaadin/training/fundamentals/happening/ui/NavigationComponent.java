package org.vaadin.training.fundamentals.happening.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.vaadin.training.fundamentals.happening.ui.edit.AddNewView;
import org.vaadin.training.fundamentals.happening.ui.list.ListHappeningsView;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class NavigationComponent extends VerticalLayout implements Navigation {

    private static final long serialVersionUID = 1L;

    private VaadinView<?> activeView;
    private Class<?> activeViewType;

    private Views views;

    public NavigationComponent() {
        buildLayout();
    }

    @SuppressWarnings("serial")
    private void buildLayout() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.setHeight("50px");
        Label appName = new Label("Happening Application Header");
        header.addComponent(appName);
        NativeButton listButton = new NativeButton("List");
        listButton.addListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                setCurrentView(ListHappeningsView.class, null);
            }
        });
        
        NativeButton addButton = new NativeButton("Add");
        addButton.addListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                setCurrentView(AddNewView.class, null);
            }
        });        
        
        NativeButton logoutButton = new NativeButton("Logout");
        listButton.addListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                
            }
        });
        header.addComponent(listButton);
        header.addComponent(addButton);
        header.addComponent(logoutButton);
        addComponent(header);
    }

    public VaadinView<?> setCurrentView(Class<?> type, Map<String, String> params) {
        if (activeView != null && activeViewType.equals(type)) {
            // do nothing, since requested viewMock already active
            return activeView;
        }

        try {
            // Create new viewMock
            VaadinView<?> newView = views.newInstance(type);

            // Initialize selected viewMock (once)
            newView.init(this, params);

            Component newContent = newView.getViewContent();

            // Remove current viewMock if one exists
            if (activeView != null) {
                removeComponent(activeView.getViewContent());
            }

            activeView = newView;
            activeViewType = type;

            // Add new viewMock
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
