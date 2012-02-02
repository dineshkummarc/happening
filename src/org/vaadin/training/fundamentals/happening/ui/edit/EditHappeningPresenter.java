package org.vaadin.training.fundamentals.happening.ui.edit;

import java.util.Map;

import org.vaadin.training.fundamentals.happening.domain.entity.DomainUser;
import org.vaadin.training.fundamentals.happening.domain.entity.Happening;
import org.vaadin.training.fundamentals.happening.domain.impl.DomainUtils;
import org.vaadin.training.fundamentals.happening.ui.AppData;
import org.vaadin.training.fundamentals.happening.ui.PresenterInitFailedException;
import org.vaadin.training.fundamentals.happening.ui.edit.EditHappeningView.ItemSavedEvent;
import org.vaadin.training.fundamentals.happening.ui.edit.EditHappeningView.ItemSavedListener;
import org.vaadin.training.fundamentals.happening.ui.edit.EditHappeningView.SaveCanceledEvent;
import org.vaadin.training.fundamentals.happening.ui.edit.EditHappeningView.SaveCanceledListener;
import org.vaadin.training.fundamentals.happening.ui.list.ListHappeningsView;

import com.vaadin.data.util.BeanItem;

public class EditHappeningPresenter implements EditHappeningView.ItemSavedListener, EditHappeningView.SaveCanceledListener {

    private static final long serialVersionUID = 1L;
    private final EditHappeningView<?> view;
    
    public EditHappeningPresenter(EditHappeningView<?> view) {
        this.view = view;
        view.addListener((ItemSavedListener)this);
        view.addListener((SaveCanceledListener)this);
    }
    
    public void init(Map<String, String> params) throws PresenterInitFailedException {
        if (params != null) {
            Long id = Long.valueOf(params.get("id"));
            Happening happening = AppData.getDomain().find(Happening.class, id);
            if (happening != null) {
                view.setDatasource(new BeanItem<Happening>(happening));
            } else {
                throw new PresenterInitFailedException("Cannot find entity with id: " + id);
            }
        } else {
            view.setDatasource(new BeanItem<Happening>(new Happening()));
        }
    }

    public void saveCanceled(SaveCanceledEvent event) {
        view.navigateTo(ListHappeningsView.class, null);
    }

    public void itemSaved(ItemSavedEvent event) {
        Happening happening = (Happening)event.getBean();
        DomainUser currentUser = AppData.getCurrentUser();
        boolean setCookie = false;
        if (happening.getOwner() != null && currentUser != null && currentUser.getId().equals(happening.getOwner().getId())) {
            happening = AppData.getDomain().store(happening);
            currentUser = AppData.getDomain().find(DomainUser.class, currentUser.getId());
        } else if (happening.getOwner() == null) {
            if (currentUser == null) {
                currentUser = DomainUtils.createUser();
                setCookie = true;
            }
            currentUser.getHappenings().add(happening);
            happening.setOwner(currentUser);
            happening = AppData.getDomain().store(happening);
        }
        AppData.setCurrentUser(currentUser);
        if (setCookie) {
            AppData.setUserCookie();
        }
            
        view.showSaveSuccess();
        view.setDatasource(new BeanItem<Happening>(happening));
    }
}