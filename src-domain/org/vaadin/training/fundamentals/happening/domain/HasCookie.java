package org.vaadin.training.fundamentals.happening.domain;

public interface HasCookie {
    String getCookie();
    void clearCookie();
    void setCookie(String data);
}
