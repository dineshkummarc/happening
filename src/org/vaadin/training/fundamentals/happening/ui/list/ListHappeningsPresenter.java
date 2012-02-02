package org.vaadin.training.fundamentals.happening.ui.list;

import java.util.Collections;
import java.util.List;

import com.vaadin.data.util.BeanItemContainer;

import org.vaadin.training.fundamentals.happening.domain.entity.DomainUser;
import org.vaadin.training.fundamentals.happening.domain.entity.Happening;
import org.vaadin.training.fundamentals.happening.ui.AppData;

public class ListHappeningsPresenter {

    private ListHappeningsView<?> view;
    
    public ListHappeningsPresenter(ListHappeningsView<?> view) {
        this.view = view;
    }
    
    public void init() {
        BeanItemContainer<Happening> container = new BeanItemContainer<Happening>(Happening.class);
//        BeanItemContainer<DomainUser> container = new BeanItemContainer<DomainUser>(DomainUser.class);
        if (AppData.getCurrentUser() != null) {
            List<Happening> happenings = AppData.getDomain().list(Happening.class, "SELECT h FROM Happening h WHERE h.owner = :owner", Collections.singletonMap("owner", (Object) AppData.getCurrentUser()));
//            List<DomainUser> happenings = AppData.getDomain().list(DomainUser.class);
            container.addAll(happenings);
            container.addNestedContainerProperty("owner.hash");
        }
        view.setDatasource(container);
    }
}
