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
import java.util.concurrent.ConcurrentHashMap;

import org.vaadin.training.fundamentals.happening.ui.view.VaadinView;

public class ViewsImpl implements Views {
    public ViewsImpl() {

    }

    private final Map<Class<?>, ViewProvider> providers = new ConcurrentHashMap<Class<?>, ViewProvider>();

    public void addProvider(Class<?> viewType, ViewProvider p) {
        providers.put(viewType, p);
    }

    public VaadinView<?> newInstance(Class<?> viewType) {
        ViewProvider p = providers.get(viewType);
        if (p == null) {
            throw new IllegalArgumentException(
                    "No provider registered with type: "
                            + viewType.getSimpleName());
        }
        return p.newView();
    }
}
