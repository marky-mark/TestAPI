package com.mtt.email.preperation.impl;

import com.mtt.email.preperation.EmailUrlScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Default implementation of {@link EmailUrlScheme}.
 */
@Component
public final class DefaultEmailUrlScheme implements EmailUrlScheme {

    @Value("${mtt.web.base_uri:http://my.mtt.com}")
    private String baseUri;

    @Value("${mtt.web.https.base_uri:https://my.mtt.com}")
    private String httpsBaseUri;

    @Override
    public String createUserActivationUrl(String username, String activationKey) {
        StringBuilder urlBuilder = new StringBuilder(baseUri);
        urlBuilder.append("/activate-user?id=").append(username).append("&key=").append(activationKey);
        return urlBuilder.toString();
    }
}
