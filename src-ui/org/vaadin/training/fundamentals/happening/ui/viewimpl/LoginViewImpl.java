package org.vaadin.training.fundamentals.happening.ui.viewimpl;

import java.util.Map;
import java.util.ResourceBundle;

import org.vaadin.training.fundamentals.happening.ui.AppData;
import org.vaadin.training.fundamentals.happening.ui.Navigation;
import org.vaadin.training.fundamentals.happening.ui.NoAccessException;
import org.vaadin.training.fundamentals.happening.ui.NotAuthenticatedException;
import org.vaadin.training.fundamentals.happening.ui.view.ListHappeningsView;
import org.vaadin.training.fundamentals.happening.ui.view.LoginView;
import org.vaadin.training.fundamentals.happening.ui.view.VaadinView;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class LoginViewImpl extends CssLayout implements LoginView<CssLayout>,
        VaadinView.HasRefresh {

    private static final long serialVersionUID = 1L;
    private CustomLayout loginLayout;
    private Navigation navigation;
    private ResourceBundle tr;

    @Override
    public void init(Navigation navigation, Map<String, String> params)
            throws NotAuthenticatedException, NoAccessException {
        tr = AppData.getTr(AppData.getLocale());
        final Label label = new Label(
                tr.getString("LoginView.PlaceholderMessage"));
        addComponent(label);
    }

    @Override
    public void refresh() {
        showLoginWindow();
    }

    @Override
    public void attach() {
        super.attach();
        showLoginWindow();
    }

    private void showLoginWindow() {
        loginLayout = new CustomLayout("loginlayout");
        Window loginWindow = new Window(tr.getString("LoginWindow.Caption"),
                loginLayout);
        loginWindow.setWidth("60%");
        loginWindow.setHeight("60%");
        loginWindow.setClosable(false);
        loginWindow.setModal(true);
        loginWindow.center();
        getWindow().addWindow(loginWindow);
    }

    @Override
    public <T extends VaadinView<?>> void navigateTo(Class<T> view,
            Map<String, String> params) {
        navigation.setCurrentView(ListHappeningsView.class, null);
    }

    @Override
    public CssLayout asComponent() {
        return this;
    }
}
