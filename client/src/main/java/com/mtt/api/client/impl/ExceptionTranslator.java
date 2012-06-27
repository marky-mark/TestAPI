package com.mtt.api.client.impl;

import org.jboss.resteasy.client.ClientResponseFailure;

/**
 * Translate RESTEasy error response.
 */
public interface ExceptionTranslator {

    /**
     * Translate RESTEasy error response.
     *
     * @param failure the failure
     * @return failure translated into an exception.
     */
    RuntimeException translateResponseFailure(ClientResponseFailure failure);
}
