package org.vaadin.training.fundamentals.happening.ui.list;

import java.io.Serializable;
import java.util.Map;

import org.vaadin.training.fundamentals.happening.ui.Navigation;
import org.vaadin.training.fundamentals.happening.ui.VaadinView;

import com.vaadin.data.Container;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class ListHappeningsViewImpl implements ListHappeningsView<VerticalLayout>, Serializable {

    private static final long serialVersionUID = 1L;
    private VerticalLayout layout;
    private Table table;
    private Navigation navigation;
    private ListHappeningsPresenter presenter;
    
    public void init(Navigation navigation, Map<String, String> params) {
        presenter = new ListHappeningsPresenter(this);
        this.navigation = navigation;
        layout = new VerticalLayout();
        layout.setSizeFull();
        table = new Table("Your events");
        table.setSizeFull();
        layout.addComponent(table);
        presenter.init();
    }

    public VerticalLayout getViewContent() {
        return layout;
    }

    public <T extends VaadinView<?>> void navigateTo(Class<T> view, Map<String, String> params) {
        navigation.setCurrentView(view, params);
    }

    @Override
    public void setDatasource(Container container) {
        table.setContainerDataSource(container);
    }

}
