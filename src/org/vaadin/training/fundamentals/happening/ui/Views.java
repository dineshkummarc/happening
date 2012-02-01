package org.vaadin.training.fundamentals.happening.ui;

public interface Views {

    public void addProvider(Class<?> viewType, ViewProvider p);
    
    public VaadinView<?> newInstance(Class<?> viewType);
}
