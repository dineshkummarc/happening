package org.vaadin.training.fundamentals.happening.ui.presenter;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.vaadin.training.fundamentals.happening.domain.entity.DomainUser;
import org.vaadin.training.fundamentals.happening.domain.impl.DomainUtils;
import org.vaadin.training.fundamentals.happening.ui.AppData;
import org.vaadin.training.fundamentals.happening.ui.view.ListHappeningsView;
import org.vaadin.training.fundamentals.happening.ui.view.LoginView;
import org.vaadin.training.fundamentals.happening.ui.view.LoginView.LoginAttemptEvent;
import org.vaadin.training.fundamentals.happening.ui.view.LoginView.LoginAttemptEventListener;

public class LoginPresenter {

    private final LoginView<?> view;
    private boolean userDataGenerated;

    public LoginPresenter(LoginView<?> view) {
        this.view = view;
        view.addListener(loginListener);
    }

    public void init() {
        if (!userDataGenerated) {
            long count = AppData.getDomain().count(DomainUser.class);
            if (count == 0) {
                DomainUser user = DomainUtils.createUser("password");
                user.setAccountId("user1");
                user.setName("John Doe");
                AppData.getDomain().store(user);
            }
            userDataGenerated = true;
        }

    }

    private final LoginAttemptEventListener loginListener = new LoginAttemptEventListener() {

        private static final long serialVersionUID = 1L;

        @Override
        public void login(LoginAttemptEvent event) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("accountId", event.getAccountId());
            DomainUser loggedInUser = null;
            DomainUser candidate = AppData
                    .getDomain()
                    .find("SELECT u FROM DomainUser u WHERE u.accountId = :accountId",
                            params);
            if (candidate != null) {
                try {
                    String triedHash = DomainUtils.hash(event.getSecret(),
                            candidate.getSalt());
                    if (triedHash.equals(candidate.getHash())) {
                        // We have a valid user
                        loggedInUser = candidate;
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } finally {
                    AppData.setCurrentUser(loggedInUser);
                }
            }

            view.navigateTo(ListHappeningsView.class, null);
        }
    };
}
