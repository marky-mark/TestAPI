package com.mtt.api.client.util.security;

/**
 * A {@link RequestSigner} is responsible for signing an outgoing request.
 */
public interface RequestSigner {

    /**
     * Sign the given request.
     *
     * @param request the request to sign.
     */
    void signRequest(SignableRequest request);
}
