package org.vaadin.training.fundamentals.happening.ui;

import java.util.Map;

public interface Navigation {

    public void setViews(Views views);
    
    public VaadinView<?> setCurrentView(Class<?> type, Map<String, String> params);
}
