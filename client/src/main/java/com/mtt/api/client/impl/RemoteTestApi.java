package com.mtt.api.client.impl;

import com.mtt.api.client.TestAPI;
import com.mtt.api.client.domain.TaskApi;
import com.mtt.api.client.util.RequestSigningClientExecutor;
import com.mtt.api.model.MTTApiKey;
import org.apache.http.client.HttpClient;
import org.jboss.resteasy.client.ProxyFactory;

public class RemoteTestApi implements TestAPI {

//    static {
//        ResteasyProviderFactory factory = ResteasyProviderFactory.getInstance();
//        factory.addContextResolver(new JacksonContextResolver());
//        RegisterBuiltin.register(factory);
//    }

    private TaskApi taskApi;

    public TaskApi taskApi() {
        return taskApi;
    }

    public RemoteTestApi(String baseUri,
                         MTTApiKey defaultApiKey,
                         HttpClient httpClient) {

        MTTApiRequestSigner requestSigner = new MTTApiRequestSigner(defaultApiKey);

        //create the extended ApacheHttpClient4Executor with the request signer - this uses httpClient with RestEasy
        taskApi = ProxyFactory.create(TaskApi.class, baseUri, new RequestSigningClientExecutor(httpClient, requestSigner));
    }
}
