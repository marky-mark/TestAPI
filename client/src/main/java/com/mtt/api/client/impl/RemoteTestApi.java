package com.mtt.api.client.impl;

import com.mtt.api.client.TestAPI;
import com.mtt.api.client.domain.TaskApi;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;

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
            ApacheHttpClient4Executor client4Executor) {
        taskApi = ProxyFactory.create(TaskApi.class, baseUri, client4Executor);
    }
}
