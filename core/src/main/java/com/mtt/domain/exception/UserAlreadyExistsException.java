package com.mtt.domain.exception;

/**
 * Exception to be thrown when a requested user cannot be found
 */
public final class UserAlreadyExistsException extends RuntimeException {

    private String username;

    /**
     * Constructor.
     *
     * @param username the username that already exists
     */
    public UserAlreadyExistsException(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
