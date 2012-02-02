package org.vaadin.training.fundamentals.happening.ui;

import java.util.Map;

import com.vaadin.ui.Component;

public interface VaadinView<T extends Component> extends Navigates {

    public void init(Navigation navigation, Map<String, String> params);
    public T getViewContent();
}
