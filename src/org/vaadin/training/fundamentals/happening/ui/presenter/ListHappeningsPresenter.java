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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.data.util.BeanItemContainer;

import org.vaadin.training.fundamentals.happening.domain.entity.Happening;
import org.vaadin.training.fundamentals.happening.ui.AppData;
import org.vaadin.training.fundamentals.happening.ui.view.ListHappeningsView;
import org.vaadin.training.fundamentals.happening.ui.view.ShowHappeningView;
import org.vaadin.training.fundamentals.happening.ui.view.ListHappeningsView.ItemOpenEvent;
import org.vaadin.training.fundamentals.happening.ui.view.ListHappeningsView.ItemOpenListener;

public class ListHappeningsPresenter {

    private ListHappeningsView<?> view;
    
    public ListHappeningsPresenter(ListHappeningsView<?> view) {
        this.view = view;
    }
    
    public void init() {
        view.addListener(itemOpenListener);
        BeanItemContainer<Happening> container = new BeanItemContainer<Happening>(Happening.class);
        if (AppData.getCurrentUser() != null) {
            List<Happening> happenings = AppData.getDomain().list(Happening.class, "SELECT h FROM Happening h WHERE h.owner = :owner", Collections.singletonMap("owner", (Object) AppData.getCurrentUser()));
            container.addAll(happenings);
            container.addNestedContainerProperty("owner.hash");
        }
        view.setDatasource(container);
    }
    
    private final ItemOpenListener itemOpenListener = new ItemOpenListener() {
        
        private static final long serialVersionUID = 1L;

        @Override
        public void itemOpen(ItemOpenEvent event) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("id", String.valueOf(event.getBean().getId()));
            view.navigateTo(ShowHappeningView.class, params);
        }
    };
}
