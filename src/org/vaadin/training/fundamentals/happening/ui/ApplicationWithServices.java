package org.vaadin.training.fundamentals.happening.ui;

import com.vaadin.ui.UriFragmentUtility;

public interface ApplicationWithServices {
    String getCookie();
    void clearCookie();
    void setCookie(String data);
    UriFragmentUtility getUriFragmentUtility();
}
