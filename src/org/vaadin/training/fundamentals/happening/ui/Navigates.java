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

package org.vaadin.training.fundamentals.happening.ui;

import java.util.Map;

import org.vaadin.training.fundamentals.happening.ui.view.VaadinView;

/**
 * Base interface for {@link VaadinView} and as such for all of the views in the
 * application's Model-View-Presenter based architecture.
 * 
 * @author Johannes
 * 
 */
public interface Navigates {
    public <T extends VaadinView<?>> void navigateTo(Class<T> view,
            Map<String, String> params);

    /**
     * Specialized {@link Navigates} view that needs to user prompt before
     * {@link Navigation} navigates away from this view.
     * 
     * @author Johannes
     * 
     */
    public interface WithUserPrompt {

        /**
         * The view should show a user prompt and return a boolean value
         * indicating whether the prompt was shown or not. The view should also
         * store the callback interface and use it to commmunicate to the
         * Navigation that it is ok to do the pending view change.
         * 
         * @param callback
         * @return true if a user prompt was shown and {@link Navigation} should
         *         not proceed with changing the current view.
         */
        boolean showUserPrompt(Navigation.PendingNavigationCallback callback);

    }
}
