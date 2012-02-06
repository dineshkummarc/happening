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

/**
 * Navigation a component, which uses @{link Views} to create new instances of
 * views and provides {@link #setCurrentView(Class, Map)} to allow changing the
 * current view.
 * 
 * @author Johannes
 * 
 */
public interface Navigation {

    void setViews(Views views);

    void setCurrentView(Class<?> type, Map<String, String> params);

    /**
     * Callback for a pending navigation. Used by
     * {@link Navigates#WithUserPrompt} views to signal canceling or accepting
     * navigation away from it. It's up to the implementor to decide how to keep
     * track if the pending navigation is still valid or has the view change
     * already occurred.
     * 
     * @author Johannes
     * 
     */
    public interface PendingNavigationCallback {

        /**
         * Ok to navigate away from this pending view navigation.
         */
        void commit();

        /**
         * Not ok to navigate away from this pending view navigation.
         */
        void discard();
    }
}
