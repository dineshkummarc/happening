package org.vaadin.training.fundamentals.happening.ui;

public class PresenterInitFailedException extends Exception {

    private static final long serialVersionUID = 1L;

    public PresenterInitFailedException(String message) {
        super(message);
    }
    
    public PresenterInitFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
