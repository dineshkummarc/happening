package org.vaadin.training.fundamentals.happening.ui;

import org.vaadin.training.fundamentals.happening.ui.view.VaadinView;

public interface ViewProvider {
    public VaadinView<?> newView();
}
