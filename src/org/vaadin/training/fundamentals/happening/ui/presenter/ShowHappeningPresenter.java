package org.vaadin.training.fundamentals.happening.ui.presenter;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.training.fundamentals.happening.domain.entity.Happening;
import org.vaadin.training.fundamentals.happening.ui.AppData;
import org.vaadin.training.fundamentals.happening.ui.PresenterInitFailedException;
import org.vaadin.training.fundamentals.happening.ui.view.EditHappeningView;
import org.vaadin.training.fundamentals.happening.ui.view.ShowHappeningView;
import org.vaadin.training.fundamentals.happening.ui.view.ShowHappeningView.EditSelectedEvent;
import org.vaadin.training.fundamentals.happening.ui.view.ShowHappeningView.EditSelectedListener;

import com.vaadin.data.util.BeanItem;

public class ShowHappeningPresenter {

    ShowHappeningView<?> view;
    BeanItem<Happening> item;

    public ShowHappeningPresenter(ShowHappeningView<?> view) {
        this.view = view;
        view.addListener(editSelectedListener);
    }

    public void init(Map<String, String> params)
            throws PresenterInitFailedException {
        if (params != null) {
            Long id = params.containsKey("id") ? Long.parseLong(params
                    .get("id")) : null;
            if (id != null) {
                Happening bean = AppData.getDomain().find(Happening.class, id);
                item = new BeanItem<Happening>(bean);
                view.setDataSource(item);
                return;
            }

        }
        throw new PresenterInitFailedException(
                "Presenter was initiated with invalid id");
    }

    private final EditSelectedListener editSelectedListener = new EditSelectedListener() {
        private static final long serialVersionUID = 1L;

        @Override
        public void editSelected(EditSelectedEvent event) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("id", String.valueOf(item.getBean().getId()));
            view.navigateTo(EditHappeningView.class, params);
        }
    };
}
