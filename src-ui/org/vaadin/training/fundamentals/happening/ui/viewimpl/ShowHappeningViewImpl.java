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

import java.lang.reflect.Method;
import java.util.Map;
import java.util.ResourceBundle;

import org.vaadin.training.fundamentals.happening.domain.entity.Happening;
import org.vaadin.training.fundamentals.happening.ui.AppData;
import org.vaadin.training.fundamentals.happening.ui.Navigation;
import org.vaadin.training.fundamentals.happening.ui.NoAccessException;
import org.vaadin.training.fundamentals.happening.ui.NotAuthenticatedException;
import org.vaadin.training.fundamentals.happening.ui.PresenterInitFailedException;
import org.vaadin.training.fundamentals.happening.ui.presenter.ShowHappeningPresenter;
import org.vaadin.training.fundamentals.happening.ui.view.ShowHappeningView;
import org.vaadin.training.fundamentals.happening.ui.view.VaadinView;

import com.vaadin.data.util.BeanItem;
import com.vaadin.event.EventRouter;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

public class ShowHappeningViewImpl extends GridLayout implements
        ShowHappeningView<GridLayout> {

    private static final long serialVersionUID = 1L;

    private ShowHappeningPresenter presenter;
    private Navigation navigation;
    private EventRouter eventRouter;

    @Override
    public void init(Navigation navigation, Map<String, String> params)
            throws NotAuthenticatedException, NoAccessException {
        this.navigation = navigation;
        eventRouter = new EventRouter();
        setSizeFull();
        rebuildLayout();
        presenter = new ShowHappeningPresenter(this);
        try {
            presenter.init(params);
        } catch (PresenterInitFailedException e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("serial")
    private void rebuildLayout() {
        removeAllComponents();
        ResourceBundle tr = AppData.getTr(AppData.getLocale());
        Button editButton = new Button(tr.getString("Button.Edit.Caption"));
        editButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                eventRouter.fireEvent(new EditSelectedEvent(event.getButton()));
            }
        });
        addComponent(editButton, 0, 0, 1, 0);
        setComponentAlignment(editButton, Alignment.TOP_RIGHT);
    }

    @Override
    public <T extends VaadinView<?>> void navigateTo(Class<T> view,
            Map<String, String> params) {
        navigation.setCurrentView(view, params);
    }

    public void setDataSource(BeanItem<Happening> item) {
        rebuildLayout();
        for (Object id : item.getItemPropertyIds()) {
            addComponent(new Label(id.toString()));
            addComponent(new Label(item.getItemProperty(id)));
        }
    }

    @Override
    public void addListener(EditSelectedListener listener) {
        try {
            Method method = EditSelectedListener.class.getDeclaredMethod(
                    "editSelected", new Class[] { EditSelectedEvent.class });
            eventRouter.addListener(EditSelectedEvent.class, listener, method);
        } catch (final java.lang.NoSuchMethodException e) {
            // This should never happen
            throw new java.lang.RuntimeException(
                    "Internal error, editSelected method not found");
        }
    }

    @Override
    public void removeListener(EditSelectedListener listener) {
        eventRouter.removeListener(EditSelectedListener.class, listener);
    }

    @Override
    public GridLayout asComponent() {
        return this;
    }

}
