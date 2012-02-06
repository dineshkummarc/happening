package org.vaadin.training.fundamentals.happening.ui.viewimpl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.vaadin.training.fundamentals.happening.domain.entity.Happening;
import org.vaadin.training.fundamentals.happening.ui.Navigates;
import org.vaadin.training.fundamentals.happening.ui.Navigation;
import org.vaadin.training.fundamentals.happening.ui.Navigation.PendingNavigationCallback;
import org.vaadin.training.fundamentals.happening.ui.presenter.EditHappeningPresenter;
import org.vaadin.training.fundamentals.happening.ui.view.EditHappeningView;
import org.vaadin.training.fundamentals.happening.ui.view.VaadinView;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.EventRouter;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

public class EditHappeningViewImpl implements
        EditHappeningView<VerticalLayout>, Button.ClickListener, Serializable,
        Navigates.WithUserPrompt {

    private static final long serialVersionUID = 1L;

    private Form form;
    private VerticalLayout layout;
    private final EditHappeningPresenter presenter;
    private Button saveButton;
    private Button cancelButton;
    private EventRouter eventRouter;
    private PendingNavigationCallback currentCallback;

    private Navigation navigation;

    private static final Collection<String> basicProperties = Arrays
            .asList(new String[] { "title", "venue", "starts", "ends", "live" });

    // private static final Collection<String> allProperties = Arrays.asList(new
    // String[] { "title", "venue", "starts", "ends", "latitude", "longitude",
    // "live" } );

    public EditHappeningViewImpl() {
        eventRouter = new EventRouter();
        presenter = new EditHappeningPresenter(this);
    }

    public void init(Navigation navigation, Map<String, String> params) {
        this.navigation = navigation;
        buildLayout();
        try {
            presenter.init(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public VerticalLayout getViewContent() {
        return layout;
    }

    public void setDatasource(BeanItem<Happening> item) {
        form.setItemDataSource(item, basicProperties);
    }

    public void buttonClick(ClickEvent event) {
        if (event.getButton() == saveButton) {
            form.commit();
            eventRouter.fireEvent(new ItemSavedEvent(form, (BeanItem<?>) form
                    .getItemDataSource()));
        } else if (event.getButton() == cancelButton) {
            form.discard();
            eventRouter.fireEvent(new SaveCanceledEvent(form));
        }
    }

    public void showSaveSuccess() {
        layout.getWindow().showNotification("Saved",
                Notification.TYPE_TRAY_NOTIFICATION);
    }

    public void addListener(ItemSavedListener listener) {
        try {
            Method method = ItemSavedListener.class.getDeclaredMethod(
                    "itemSaved", new Class[] { ItemSavedEvent.class });
            eventRouter.addListener(ItemSavedEvent.class, listener, method);
        } catch (final java.lang.NoSuchMethodException e) {
            // This should never happen
            throw new java.lang.RuntimeException(
                    "Internal error, itemSaved method not found");
        }
    }

    public void removeListener(ItemSavedListener listener) {
        eventRouter.removeListener(ItemSavedEvent.class, listener);
    }

    public void addListener(SaveCanceledListener listener) {
        try {
            Method method = SaveCanceledListener.class.getDeclaredMethod(
                    "saveCanceled", new Class[] { SaveCanceledEvent.class });
            eventRouter.addListener(SaveCanceledEvent.class, listener, method);
        } catch (final java.lang.NoSuchMethodException e) {
            // This should never happen
            throw new java.lang.RuntimeException(
                    "Internal error, saveCanceled method not found");
        }
    }

    public void removeListener(SaveCanceledListener listener) {
        eventRouter.removeListener(SaveCanceledEvent.class, listener);
    }

    private void buildLayout() {
        layout = new VerticalLayout();
        form = new Form();
        form.setFormFieldFactory(new HappeningFieldFactory());
        form.setImmediate(true);
        form.setWriteThrough(false);
        saveButton = new Button("Save");
        saveButton.addListener(this);
        cancelButton = new Button("Cancel");
        cancelButton.addListener(this);
        layout.addComponent(form);
        form.getFooter().addComponent(saveButton);
        form.getFooter().addComponent(cancelButton);
    }

    private static class HappeningFieldFactory extends DefaultFieldFactory {

        private static final long serialVersionUID = 1L;

        public Field createField(Item item, Object propertyId,
                Component uiContext) {
            Field field = super.createField(item, propertyId, uiContext);
            if (field instanceof TextField) {
                ((TextField) field).setNullRepresentation("");
            }
            return field;
        }
    }

    public <T extends VaadinView<?>> void navigateTo(Class<T> view,
            Map<String, String> params) {
        navigation.setCurrentView(view, params);
    }

    @SuppressWarnings("serial")
    @Override
    public boolean showUserPrompt(PendingNavigationCallback callback) {
        if (form.isModified()) {
            currentCallback = callback;
            final Window confirmWindow = new Window("Are you sure?");
            Button okButton = new Button("Ok");
            okButton.addListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                    layout.getWindow().removeWindow(confirmWindow);
                    if (currentCallback != null) {
                        currentCallback.commit();
                    }
                }
            });
            Button cancelButton = new Button("Cancel");
            cancelButton.addListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                    layout.getWindow().removeWindow(confirmWindow);
                    if (currentCallback != null) {
                        currentCallback.discard();
                    }                    
                }
            });
            confirmWindow.addComponent(okButton);
            confirmWindow.addComponent(cancelButton);
            layout.getWindow().addWindow(confirmWindow);
            return true;
        }
        return false;
    }
}
