package org.vaadin.training.fundamentals.happening.ui.viewimpl;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.ResourceBundle;

import org.vaadin.training.fundamentals.happening.ui.AppData;
import org.vaadin.training.fundamentals.happening.ui.Navigation;
import org.vaadin.training.fundamentals.happening.ui.NoAccessException;
import org.vaadin.training.fundamentals.happening.ui.NotAuthenticatedException;
import org.vaadin.training.fundamentals.happening.ui.presenter.LoginPresenter;
import org.vaadin.training.fundamentals.happening.ui.view.LoginView;
import org.vaadin.training.fundamentals.happening.ui.view.VaadinView;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.LoginForm.LoginListener;
import com.vaadin.ui.Window;

public class LoginViewImpl extends CssLayout implements LoginView<CssLayout>,
        VaadinView.HasRefresh {

    private static final long serialVersionUID = 1L;
    private CssLayout loginLayout;
    private Navigation navigation;
    private ResourceBundle tr;
    private LoginPresenter presenter;
    private Window loginWindow;

    @Override
    public void init(Navigation navigation, Map<String, String> params)
            throws NotAuthenticatedException, NoAccessException {
        this.navigation = navigation;
        tr = AppData.getTr(AppData.getLocale());
        final Label label = new Label(
                tr.getString("LoginView.PlaceholderMessage"));
        label.setSizeUndefined();
        addComponent(label);
        presenter = new LoginPresenter(this);
        presenter.init();
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

    @SuppressWarnings("serial")
    private void showLoginWindow() {
        if (loginWindow != null && loginWindow.getParent() != null) {
            return;
        }
        loginLayout = new CssLayout();
        loginLayout.setSizeFull();
        loginLayout.addStyleName("login");
        loginWindow = new Window(tr.getString("LoginWindow.Caption"),
                loginLayout);
        loginWindow.setWidth("60%");
        loginWindow.setHeight("60%");
        loginWindow.setClosable(false);
        loginWindow.setModal(true);
        loginWindow.center();

        LoginForm loginForm = new LoginForm();
        loginForm.setSizeUndefined();
        loginForm.addListener(new LoginListener() {
            @Override
            public void onLogin(LoginEvent event) {
                String accountId = event.getLoginParameter("username");
                String password = event.getLoginParameter("password");
                LoginViewImpl.this.getWindow().removeWindow(
                        event.getComponent().getWindow());
                LoginViewImpl.this.fireEvent(new LoginAttemptEvent(
                        LoginViewImpl.this, accountId, password));
            }
        });

        loginLayout.addComponent(loginForm);

        // Label photoAttribution = new Label(
        // "Photo by Curtis Fry. Licensed under Creative Commons");
        // photoAttribution.setSizeUndefined();
        // photoAttribution.addStyleName("photoAttribution");
        // loginLayout.addComponent(photoAttribution);

        // TextField username = new
        // TextField(tr.getString("LoginView.Username"));
        // loginLayout.addComponent(username, "username");
        // PasswordField password = new PasswordField(
        // tr.getString("LoginView.Password"));
        // loginLayout.addComponent(password, "password");
        //
        // Button registerButton = new Button(tr.getString("Button.Register"));
        // registerButton.addListener(new Button.ClickListener() {
        // @Override
        // public void buttonClick(ClickEvent event) {
        // }
        // });
        // loginLayout.addComponent(registerButton, "registerButton");

        getWindow().addWindow(loginWindow);
    }

    @Override
    public <T extends VaadinView<?>> void navigateTo(Class<T> view,
            Map<String, String> params) {
        navigation.setCurrentView(view, params);
    }

    @Override
    public CssLayout asComponent() {
        return this;
    }

    @Override
    public void addListener(LoginAttemptEventListener listener) {
        try {
            Method method = LoginAttemptEventListener.class.getDeclaredMethod(
                    "login", new Class[] { LoginAttemptEvent.class });
            addListener(LoginAttemptEvent.class, listener, method);
        } catch (final java.lang.NoSuchMethodException e) {
            // This should never happen
            throw new java.lang.RuntimeException(
                    "Internal error, login method not found");
        }
    }

    @Override
    public void removeListener(LoginAttemptEventListener listener) {
        removeListener(LoginAttemptEvent.class, listener);
    }
}
