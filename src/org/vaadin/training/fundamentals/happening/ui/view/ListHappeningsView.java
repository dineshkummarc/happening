/* 
 * Copyright 2012 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.vaadin.training.fundamentals.happening.ui.view;

import java.io.Serializable;

import org.vaadin.training.fundamentals.happening.domain.entity.AbstractEntity;

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

        public ItemOpenEvent(Component source,
                BeanItem<? extends AbstractEntity> item) {
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
