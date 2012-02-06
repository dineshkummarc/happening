package org.vaadin.training.fundamentals.happening.ui;

import java.util.Map;

import org.vaadin.training.fundamentals.happening.ui.view.VaadinView;

public interface Navigates {
    public <T extends VaadinView<?>> void navigateTo(Class<T> view,
            Map<String, String> params);

    public interface WithUserPrompt {

        boolean showUserPrompt(Navigation.PendingNavigationCallback callback);

    }
}
