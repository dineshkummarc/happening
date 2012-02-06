package org.vaadin.training.fundamentals.happening.ui.view;

import java.util.Map;

import org.vaadin.training.fundamentals.happening.ui.Navigates;
import org.vaadin.training.fundamentals.happening.ui.Navigation;

import com.vaadin.ui.Component;

public interface VaadinView<T extends Component> extends Navigates {

    public void init(Navigation navigation, Map<String, String> params);
    public T getViewContent();
}
