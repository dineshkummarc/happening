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

package org.vaadin.training.fundamentals.happening.ui.presenter;

import java.util.Map;

import org.vaadin.training.fundamentals.happening.domain.entity.DomainUser;
import org.vaadin.training.fundamentals.happening.domain.entity.Happening;
import org.vaadin.training.fundamentals.happening.ui.AppData;
import org.vaadin.training.fundamentals.happening.ui.NoAccessException;
import org.vaadin.training.fundamentals.happening.ui.NotAuthenticatedException;
import org.vaadin.training.fundamentals.happening.ui.PresenterInitFailedException;
import org.vaadin.training.fundamentals.happening.ui.view.EditHappeningView;
import org.vaadin.training.fundamentals.happening.ui.view.ListHappeningsView;
import org.vaadin.training.fundamentals.happening.ui.view.EditHappeningView.ItemSavedEvent;
import org.vaadin.training.fundamentals.happening.ui.view.EditHappeningView.ItemSavedListener;
import org.vaadin.training.fundamentals.happening.ui.view.EditHappeningView.SaveCanceledEvent;
import org.vaadin.training.fundamentals.happening.ui.view.EditHappeningView.SaveCanceledListener;

import com.vaadin.data.util.BeanItem;

public class EditHappeningPresenter implements
        EditHappeningView.ItemSavedListener,
        EditHappeningView.SaveCanceledListener {

    private static final long serialVersionUID = 1L;
    private final EditHappeningView<?> view;

    public EditHappeningPresenter(EditHappeningView<?> view) {
        this.view = view;
        view.addListener((ItemSavedListener) this);
        view.addListener((SaveCanceledListener) this);
    }

    public void init(Map<String, String> params)
            throws PresenterInitFailedException, NoAccessException,
            NotAuthenticatedException {
        DomainUser currentUser = AppData.getCurrentUser();
        if (currentUser == null) {
            throw new NotAuthenticatedException();
        }

        if (params != null) {
            // Existing item
            Long id = Long.valueOf(params.get("id"));
            Happening happening = AppData.getDomain().find(Happening.class, id);
            if (happening != null) {
                if (happening.getOwner().getId().equals(currentUser.getId())) {
                    view.setDatasource(new BeanItem<Happening>(happening));
                } else {
                    throw new NoAccessException();
                }
            } else {
                throw new PresenterInitFailedException(
                        "Cannot find entity with id: " + id);
            }
        } else {
            // New item
            view.setDatasource(new BeanItem<Happening>(new Happening()));
        }
    }

    public void saveCanceled(SaveCanceledEvent event) {
        view.navigateTo(ListHappeningsView.class, null);
    }

    public void itemSaved(ItemSavedEvent event)
            throws NotAuthenticatedException, NoAccessException {
        Happening happening = (Happening) event.getBean();
        DomainUser currentUser = AppData.getCurrentUser();
        if (currentUser == null) {
            throw new NotAuthenticatedException();
        }
        if (happening.getOwner() != null
                && !currentUser.getId().equals(happening.getOwner().getId())) {
            throw new NoAccessException();
        }

        if (happening.getOwner() == null) {
            currentUser.getHappenings().add(happening);
            happening.setOwner(currentUser);
            happening = AppData.getDomain().store(happening);
        } else {
            happening = AppData.getDomain().store(happening);
            currentUser = AppData.getDomain().find(DomainUser.class,
                    currentUser.getId());
        }

        view.showSaveSuccess();
        view.setDatasource(new BeanItem<Happening>(happening));
        view.navigateTo(ListHappeningsView.class, null);
    }
}