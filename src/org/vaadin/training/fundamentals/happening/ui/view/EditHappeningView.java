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

import org.vaadin.training.fundamentals.happening.domain.entity.Happening;
import org.vaadin.training.fundamentals.happening.ui.NoAccessException;
import org.vaadin.training.fundamentals.happening.ui.NotAuthenticatedException;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;

public interface EditHappeningView<T extends Component> extends VaadinView<T> {

    public void setDatasource(BeanItem<Happening> item);

    public void showSaveSuccess();

    public void addListener(ItemSavedListener listener);

    public void removeListener(ItemSavedListener listener);

    public void addListener(SaveCanceledListener listener);

    public void removeListener(SaveCanceledListener listener);

    public interface ItemSavedListener extends Serializable {
        public void itemSaved(ItemSavedEvent event)
                throws NotAuthenticatedException, NoAccessException;
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
