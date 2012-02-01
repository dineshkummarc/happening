package org.vaadin.training.fundamentals.happening.ui;

import java.io.Serializable;
import java.lang.reflect.Method;

import com.vaadin.event.EventRouter;
import com.vaadin.ui.Component;

public class ViewEventRouter implements Serializable {

    private static final long serialVersionUID = 1L;
    private EventRouter eventRouter;
    
    public void addListener(Class<?> eventType, Object target, Method method) {
        if (eventRouter == null) {
            eventRouter = new EventRouter();
        }
        eventRouter.addListener(eventType, target, method);
    }
    
    public void removeListener(Class<?> eventType, Object target, Method method) {
        if (eventRouter != null) {
            eventRouter.removeListener(eventType, target, method);
        }
    }
    
    public void removeListener(Class<?> eventType, Object target) {
        if (eventRouter != null) {
            eventRouter.removeListener(eventType, target);
        }
    }    
    
    
    public void fireEvent(Component.Event event) {
        if (eventRouter != null) {
            eventRouter.fireEvent(event);
        }
    }
}
