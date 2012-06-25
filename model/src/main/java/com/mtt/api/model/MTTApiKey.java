package com.mtt.api.model;

import java.io.Serializable;

/**
 * Class modelling an api key.
 */
public final class MTTApiKey implements Serializable {

    private String accessKey;

    private String privateKey;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
