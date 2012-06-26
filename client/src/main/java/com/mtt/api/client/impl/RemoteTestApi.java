package com.mtt.api.client.impl;

import com.mtt.api.client.TestAPI;
import com.mtt.api.client.domain.TaskApi;
import com.mtt.api.client.util.ApiBase;
import com.mtt.api.client.util.JacksonContextResolver;
import com.mtt.api.client.util.RequestSigningClientExecutor;
import com.mtt.api.model.MTTApiKey;
import org.apache.http.client.HttpClient;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

public class RemoteTestApi implements TestAPI {

    static {
        ResteasyProviderFactory factory = ResteasyProviderFactory.getInstance();
        factory.addContextResolver(new JacksonContextResolver());
        RegisterBuiltin.register(factory);
    }

    //APIs
    private TaskApi taskApi;

    //Utils
    private MTTClientExecutorFactory clientExecutorFactory;
    private String baseUri;
    private MTTApiKey defaultApiKey;

    public RemoteTestApi(String baseUri,
                         MTTApiKey defaultApiKey,
                         HttpClient httpClient) {

        this.baseUri = baseUri;
        this.defaultApiKey = defaultApiKey;

        MTTApiRequestSigner requestSigner = new MTTApiRequestSigner(defaultApiKey);

        //create the extended ApacheHttpClient4Executor with the request signer - this uses httpClient with RestEasy
        taskApi = ProxyFactory.create(TaskApi.class, baseUri, new RequestSigningClientExecutor(httpClient, requestSigner));
    }

    public <T extends ApiBase> T create(Class<T> apiClass) {

        return ProxyFactory.create(
                apiClass,
                baseUri,
                clientExecutorFactory.create(new MTTApiRequestSigner(defaultApiKey)));

    }

    public <T extends ApiBase> T create(Class<T> apiClass, MTTApiKey apiKey) {
        return ProxyFactory.create(
                apiClass,
                baseUri,
                clientExecutorFactory.create(new MTTApiRequestSigner(apiKey)));
    }

    public TaskApi taskApi() {
        return taskApi;
    }
}
