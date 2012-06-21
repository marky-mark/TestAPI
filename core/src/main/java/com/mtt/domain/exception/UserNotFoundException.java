package com.mtt.domain.exception;

/**
 *  Exception that represents a user not found - id can be a id or username
 */
public final class UserNotFoundException extends RuntimeException {

    private String id;

    public UserNotFoundException(Long id) {
        this.id = id.toString();
    }

    public UserNotFoundException(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
