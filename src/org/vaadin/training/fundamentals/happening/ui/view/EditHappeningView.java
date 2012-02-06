package org.vaadin.training.fundamentals.happening.ui.view;

import java.io.Serializable;

import org.vaadin.training.fundamentals.happening.domain.entity.Happening;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;

public interface EditHappeningView<T extends Component> extends AddNewView<T> {

    public void setDatasource(BeanItem<Happening> item);

    public void showSaveSuccess();

    public void addListener(ItemSavedListener listener);

    public void removeListener(ItemSavedListener listener);

    public void addListener(SaveCanceledListener listener);

    public void removeListener(SaveCanceledListener listener);

    public interface ItemSavedListener extends Serializable {
        public void itemSaved(ItemSavedEvent event);
    }

    public interface SaveCanceledListener extends Serializable {
        public void saveCanceled(SaveCanceledEvent event);
    }

    public static class ItemSavedEvent extends Component.Event {

        private static final long serialVersionUID = 1L;

        private BeanItem<?> savedItem;

        public ItemSavedEvent(Component source, BeanItem<?> savedItem) {
            super(source);
            this.savedItem = savedItem;
        }

        public BeanItem<?> getSavedItem() {
            return savedItem;
        }
        
        public Object getBean() {
            return savedItem.getBean();
        }
    }

    public static class SaveCanceledEvent extends Component.Event {

        private static final long serialVersionUID = 1L;

        public SaveCanceledEvent(Component source) {
            super(source);
        }
    }
}
