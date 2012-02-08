package org.vaadin.training.fundamentals.happening.ui.view;

import java.io.Serializable;

import com.vaadin.ui.Component;

public interface LoginView<T extends Component> extends VaadinView<T> {

    public void addListener(LoginAttemptEventListener listener);

    public void removeListener(LoginAttemptEventListener listener);

    public interface LoginAttemptEventListener extends Serializable {
        public void login(LoginAttemptEvent event);
    }

    public static class LoginAttemptEvent extends Component.Event {

        private static final long serialVersionUID = 1L;

        private final String accountId;
        private final String secret;

        public LoginAttemptEvent(Component source, String accountId,
                String secret) {
            super(source);
            this.accountId = accountId;
            this.secret = secret;
        }

        public String getAccountId() {
            return accountId;
        }

        public String getSecret() {
            return secret;
        }
    }

}
