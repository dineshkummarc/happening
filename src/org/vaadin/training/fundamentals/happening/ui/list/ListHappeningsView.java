package org.vaadin.training.fundamentals.happening.ui.list;

import org.vaadin.training.fundamentals.happening.ui.VaadinView;

import com.vaadin.data.Container;
import com.vaadin.ui.Component;

public interface ListHappeningsView<T extends Component> extends VaadinView<T> {

    public void setDatasource(Container container);
}
