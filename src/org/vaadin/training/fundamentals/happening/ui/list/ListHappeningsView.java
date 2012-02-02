package org.vaadin.training.fundamentals.happening.ui.list;

import java.io.Serializable;

import org.vaadin.training.fundamentals.happening.domain.entity.AbstractEntity;
import org.vaadin.training.fundamentals.happening.ui.VaadinView;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;

public interface ListHappeningsView<T extends Component> extends VaadinView<T> {

    public void setDatasource(Container container);
    
    public void addListener(ItemOpenListener listener);

    public void removeListener(ItemOpenListener listener);

    public interface ItemOpenListener extends Serializable {
        public void itemOpen(ItemOpenEvent event);
    }

    public static class ItemOpenEvent extends Component.Event {

        private static final long serialVersionUID = 1L;

        private BeanItem<? extends AbstractEntity> item;

        public ItemOpenEvent(Component source, BeanItem<? extends AbstractEntity> item) {
            super(source);
            this.item = item;
        }

        public BeanItem<? extends AbstractEntity> getItem() {
            return item;
        }
        
        public AbstractEntity getBean() {
            return item.getBean();
        }
    }    
}
