package com.mtt.service;

import com.mtt.domain.entity.ApiKey;
import com.mtt.domain.entity.User;

public interface SecurityService {

        /**
     * Get an {@link ApiKey} for the given access key
     *
     * @param accessKey the access key to lookup
     * @return the {@link ApiKey} for the given access key or null if not matching key found
     */
    ApiKey getApiKey(String accessKey);

    /**
     * Create an API key for the given user.
     *
     * @param user the user to create an api key for.
     * @return an API key for the given user.
     */
    ApiKey createApiKey(User user);
}
