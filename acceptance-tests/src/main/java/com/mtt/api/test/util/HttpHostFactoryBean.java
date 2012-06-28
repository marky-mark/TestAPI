package com.mtt.api.test.util;

import org.apache.http.HttpHost;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

/**
 * Used For Acceptance tests
 */
public class HttpHostFactoryBean extends AbstractFactoryBean<HttpHost> {

    private String hostname;

    private Integer port;

    /**
     * Check that the factory is set up correctly.
     */
    @PostConstruct
    public final void init() {
        Assert.hasLength(hostname);
        Assert.notNull(port);
    }

    @Override
    public final Class<?> getObjectType() {
        return HttpHost.class;
    }

    @Override
    protected final HttpHost createInstance() throws Exception {
        return new HttpHost(hostname, port);
    }

    public final void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public final void setPort(Integer port) {
        this.port = port;
    }
}
