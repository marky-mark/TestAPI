package com.mtt.api.client.impl;

import com.mtt.api.client.TestAPI;
import com.mtt.api.model.MTTApiKey;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.util.Assert;

public class RemoteTestApiFactoryBean extends AbstractFactoryBean<TestAPI> {

    private String baseUri;

    private MTTApiKey defaultApiKey;

    private int maxConnectionsPerRoute = 50;

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public void setDefaultApiKey(MTTApiKey defaultApiKey) {
        this.defaultApiKey = defaultApiKey;
    }

    @Override
    public Class<?> getObjectType() {
        return TestAPI.class;
    }

    @Override
    protected TestAPI createInstance() throws Exception {
        Assert.hasLength(baseUri);

        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager();
        cm.setDefaultMaxPerRoute(maxConnectionsPerRoute);
        HttpClient httpClient = new DefaultHttpClient(cm);

        return new RemoteTestApi(baseUri, defaultApiKey, httpClient);
    }
}
