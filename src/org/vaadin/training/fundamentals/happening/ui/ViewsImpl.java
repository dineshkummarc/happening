package org.vaadin.training.fundamentals.happening.ui;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.vaadin.training.fundamentals.happening.ui.view.VaadinView;

public class ViewsImpl implements Views {
    public ViewsImpl() {

    }

    private final Map<Class<?>, ViewProvider> providers = new ConcurrentHashMap<Class<?>, ViewProvider>();

    public void addProvider(Class<?> viewType, ViewProvider p) {
        providers.put(viewType, p);
    }

    public VaadinView<?> newInstance(Class<?> viewType) {
        ViewProvider p = providers.get(viewType);
        if (p == null) {
            throw new IllegalArgumentException(
                    "No provider registered with type: "
                            + viewType.getSimpleName());
        }
        return p.newView();
    }
}
