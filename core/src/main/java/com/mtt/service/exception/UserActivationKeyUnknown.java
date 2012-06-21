package com.mtt.service.exception;

public class UserActivationKeyUnknown extends RuntimeException {

    String activationKey;

    public UserActivationKeyUnknown(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getActivationKey() {
        return activationKey;
    }
}
