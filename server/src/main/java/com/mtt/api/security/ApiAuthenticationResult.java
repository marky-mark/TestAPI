package com.mtt.api.security;

/**
 */
public final class ApiAuthenticationResult {

    private boolean authenticated = false;

    private String receivedSignature;

    private String expectedSignature;

    private String accessKey;

    private String timestamp;

    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getReceivedSignature() {
        return receivedSignature;
    }

    public String getExpectedSignature() {
        return expectedSignature;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public void setReceivedSignature(String receivedSignature) {
        this.receivedSignature = receivedSignature;
    }

    public void setExpectedSignature(String expectedSignature) {
        this.expectedSignature = expectedSignature;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
