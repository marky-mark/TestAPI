package com.mtt.email.exception;

/**
 * Exception throws when email content creation fails for some reason.
 */
public final class EmailContentCreationException extends RuntimeException {

    /**
     * Constructor.
     *
     * @param cause the root cause
     */
    public EmailContentCreationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor.
     *
     * @param message the error message
     */
    public EmailContentCreationException(String message) {
        super(message);
    }
}
