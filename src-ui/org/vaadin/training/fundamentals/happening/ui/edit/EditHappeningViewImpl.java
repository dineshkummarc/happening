package org.vaadin.training.fundamentals.happening.ui.edit;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.vaadin.training.fundamentals.happening.domain.entity.Happening;
import org.vaadin.training.fundamentals.happening.ui.Navigation;
import org.vaadin.training.fundamentals.happening.ui.PresenterInitFailedException;
import org.vaadin.training.fundamentals.happening.ui.VaadinView;
import org.vaadin.training.fundamentals.happening.ui.ViewEventRouter;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

public class EditHappeningViewImpl implements EditHappeningView<VerticalLayout>, Button.ClickListener, Serializable {

    private static final long serialVersionUID = 1L;

    private Form form;
    private VerticalLayout layout;
    private final EditHappeningPresenter presenter;
    private Button saveButton;
    private Button cancelButton;
    private ViewEventRouter viewEventRouter;

    private Navigation navigation;
    
    private static final Collection<String> basicProperties = Arrays.asList(new String[] { "title", "venue", "starts", "ends", "live" } );
//    private static final Collection<String> allProperties = Arrays.asList(new String[] {  "title", "venue", "starts", "ends", "latitude", "longitude", "live" } );
    
    public EditHappeningViewImpl() {
        viewEventRouter = new ViewEventRouter();
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
            viewEventRouter.fireEvent(new ItemSavedEvent(form, (BeanItem<?>)form.getItemDataSource()));
        } else if (event.getButton() == cancelButton) {
            viewEventRouter.fireEvent(new SaveCanceledEvent(form));
        }
    }
    
    public void showSaveSuccess() {
        layout.getWindow().showNotification("Saved", Notification.TYPE_TRAY_NOTIFICATION);
    }
    
    public void addListener(ItemSavedListener listener) {
        try {
            Method method = ItemSavedListener.class.getDeclaredMethod(
                    "itemSaved", new Class[] { ItemSavedEvent.class });
            viewEventRouter.addListener(ItemSavedEvent.class, listener, method);
        } catch (final java.lang.NoSuchMethodException e) {
            // This should never happen
            throw new java.lang.RuntimeException(
                    "Internal error, itemSaved method not found");
        }
    }
    
    public void removeListener(ItemSavedListener listener) {
        viewEventRouter.removeListener(ItemSavedEvent.class, listener);
    }
    
    public void addListener(SaveCanceledListener listener) {
        try {
            Method method = SaveCanceledListener.class.getDeclaredMethod(
                    "saveCanceled", new Class[] { SaveCanceledEvent.class });
            viewEventRouter.addListener(SaveCanceledEvent.class, listener, method);
        } catch (final java.lang.NoSuchMethodException e) {
            // This should never happen
            throw new java.lang.RuntimeException(
                    "Internal error, saveCanceled method not found");
        }
    }

    public void removeListener(SaveCanceledListener listener) {
        viewEventRouter.removeListener(SaveCanceledEvent.class, listener);
    }
    
    private void buildLayout() {
        layout = new VerticalLayout();
        form = new Form();
        form.setFormFieldFactory(new HappeningFieldFactory());
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
                ((TextField)field).setNullRepresentation("");
            }
            return field;
        }
    }

    public <T extends VaadinView<?>> void navigateTo(Class<T> view,
            Map<String, String> params) {
        navigation.setCurrentView(view, params);
    }
}
