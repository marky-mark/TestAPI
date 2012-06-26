package com.mtt.api.client.impl;

import com.mtt.api.client.util.RequestSigningClientExecutor;
import com.mtt.api.client.util.security.RequestSigner;
import org.apache.http.client.HttpClient;
import org.jboss.resteasy.client.ClientExecutor;

/**
 * Implementation of {@link DefaultClientExecutorFactory}.
 */
public final class DefaultClientExecutorFactory implements MTTClientExecutorFactory {

    private HttpClient httpClient;

    /**
     * Constructor.
     *
     * @param httpClient          the http client for executing requests
     */
    public DefaultClientExecutorFactory(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public ClientExecutor create(RequestSigner requestSigner) {
        return new RequestSigningClientExecutor(httpClient, requestSigner);
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }
}
