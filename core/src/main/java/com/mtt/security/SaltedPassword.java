package com.mtt.security;

/**
 * Represents a salted password.
 */
public interface SaltedPassword {

    /**
     * @return the password
     */
    String getPassword();

    /**
     * @return the salt used as part of encrypting the password
     */
    String getSalt();
}
