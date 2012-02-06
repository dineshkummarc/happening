package org.vaadin.training.fundamentals.happening.ui;

import org.vaadin.training.fundamentals.happening.ui.view.VaadinView;

public interface Views {

    public void addProvider(Class<?> viewType, ViewProvider p);
    
    public VaadinView<?> newInstance(Class<?> viewType);
}
