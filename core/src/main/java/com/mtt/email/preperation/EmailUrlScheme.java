package com.mtt.email.preperation;

/**
 * Scheme for generating URLs that appear in outbound emails.
 */
public interface EmailUrlScheme {

    /**
     * Create user activation URL.
     *
     * @param username      the username
     * @param activationKey the activationKey
     * @return the generated URL
     */
    String createUserActivationUrl(String username, String activationKey);
}
