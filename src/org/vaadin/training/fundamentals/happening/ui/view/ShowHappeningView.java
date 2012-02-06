package org.vaadin.training.fundamentals.happening.ui.view;

import java.io.Serializable;

import org.vaadin.training.fundamentals.happening.domain.entity.Happening;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;

public interface ShowHappeningView<T extends Component> extends VaadinView<T> {

    void setDataSource(BeanItem<Happening> item);

    public void addListener(EditSelectedListener listener);

    public void removeListener(EditSelectedListener listener);

    public interface EditSelectedListener extends Serializable {
        public void editSelected(EditSelectedEvent event);
    }

    public static class EditSelectedEvent extends Component.Event {

        private static final long serialVersionUID = 1L;

        public EditSelectedEvent(Component source) {
            super(source);
        }
    }
}
