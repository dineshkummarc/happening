package org.vaadin.training.fundamentals.happening.ui;

import java.util.Map;

public interface Navigates {
    public <T extends VaadinView<?>> void navigateTo(Class<T> view, Map<String, String> params);
}
