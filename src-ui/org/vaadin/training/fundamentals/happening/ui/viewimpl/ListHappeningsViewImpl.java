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

package org.vaadin.training.fundamentals.happening.ui.viewimpl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;

import org.vaadin.training.fundamentals.happening.domain.entity.Happening;
import org.vaadin.training.fundamentals.happening.ui.Navigation;
import org.vaadin.training.fundamentals.happening.ui.presenter.ListHappeningsPresenter;
import org.vaadin.training.fundamentals.happening.ui.view.ListHappeningsView;
import org.vaadin.training.fundamentals.happening.ui.view.VaadinView;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.EventRouter;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class ListHappeningsViewImpl implements ListHappeningsView<VerticalLayout>, Serializable {

    private static final long serialVersionUID = 1L;
    private VerticalLayout layout;
    private Table table;
    private Navigation navigation;
    private ListHappeningsPresenter presenter;
    private EventRouter eventRouter;
    
    @SuppressWarnings("serial")
    public void init(Navigation navigation, Map<String, String> params) {
        eventRouter = new EventRouter();
        presenter = new ListHappeningsPresenter(this);
        this.navigation = navigation;
        layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        table = new Table("Your events");
        table.setSelectable(true);
        table.addListener(new ItemClickListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void itemClick(ItemClickEvent event) {
                if (table.getValue() != null) {
                    eventRouter.fireEvent(new ItemOpenEvent(table, (BeanItem<Happening>)event.getItem()));
                }
            }
        });
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

    @Override
    public void addListener(ItemOpenListener listener) {
        try {
            Method method = ItemOpenListener.class.getDeclaredMethod(
                    "itemOpen", new Class[] { ItemOpenEvent.class });
            eventRouter.addListener(ItemOpenEvent.class, listener, method);
        } catch (final java.lang.NoSuchMethodException e) {
            // This should never happen
            throw new java.lang.RuntimeException(
                    "Internal error, itemOpen method not found");
        }        
    }

    @Override
    public void removeListener(ItemOpenListener listener) {
        eventRouter.removeListener(ItemOpenListener.class, listener);
    }

}
