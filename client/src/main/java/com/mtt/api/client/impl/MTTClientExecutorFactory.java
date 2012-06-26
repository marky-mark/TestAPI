package com.mtt.api.client.impl;

import com.mtt.api.client.util.security.RequestSigner;
import org.jboss.resteasy.client.ClientExecutor;

/**
 * Factory for creating client executors.
 */
public interface MTTClientExecutorFactory {

    /**
     * Create a client executor using the given {@link com.mtt.api.model.MTTApiKey}.
     *
     * @param requestSigner for signing outgoing requests
     * @return a client executor using the given {@link com.mtt.api.model.MTTApiKey}.
     */
    ClientExecutor create(RequestSigner requestSigner);
}
