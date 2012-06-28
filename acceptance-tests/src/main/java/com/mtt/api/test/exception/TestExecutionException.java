package com.mtt.api.test.exception;

/**
 * Exception to be thrown during acceptance test processing when an error occurs.
 */
public final class TestExecutionException extends RuntimeException {

    /**
     * Constructor.
     *
     * @param cause the cause
     */
    public TestExecutionException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor.
     *
     * @param message the error message
     */
    public TestExecutionException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message the error message
     * @param cause   the cause
     */
    public TestExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
