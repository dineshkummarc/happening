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

import java.util.Map;

import org.vaadin.training.fundamentals.happening.ui.HasNavigation;
import org.vaadin.training.fundamentals.happening.ui.Navigation;
import org.vaadin.training.fundamentals.happening.ui.NoAccessException;
import org.vaadin.training.fundamentals.happening.ui.NotAuthenticatedException;

import com.vaadin.ui.Component;

/**
 * View in model view presenter
 * 
 * @author Johannes
 * 
 * @param <T>
 */
public interface VaadinView<T extends Component> extends HasNavigation {

    void init(Navigation navigation, Map<String, String> params)
            throws NotAuthenticatedException, NoAccessException;

    T asComponent();

    /**
     * The VaadView should be initialized for every navigation i.e. refresh() is
     * called when the view is set as active view and it's already the current
     * active view.
     * 
     * @author Johannes
     * 
     */
    public interface HasRefresh {
        void refresh();
    }
}
