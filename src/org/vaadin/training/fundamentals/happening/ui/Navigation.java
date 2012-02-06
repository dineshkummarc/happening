package org.vaadin.training.fundamentals.happening.ui;

import java.util.Map;

public interface Navigation {

    void setViews(Views views);

    void setCurrentView(Class<?> type, Map<String, String> params);

    public interface PendingNavigationCallback {

        void commit();

        void discard();
    }
}
