package com.mtt.service.impl;

import com.mtt.domain.entity.ApiKey;
import com.mtt.domain.entity.User;
import com.mtt.repository.ApiKeyRepository;
import com.mtt.service.SecurityService;
import com.mtt.util.RandomValueGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@DependsOn("apiKeyRepository")
@Component
public class SecurityServiceImpl implements SecurityService {

    public static final int API_KEY_LENGTH = 32;

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private RandomValueGenerator valueGenerator;

    @Override
    public ApiKey getApiKey(String accessKey) {
        return apiKeyRepository.findByAccessKey(accessKey);
    }

    @Override
    public ApiKey createApiKey(User user) {
        ApiKey apiKey = new ApiKey();
        apiKey.setAccessKey(valueGenerator.secureRandomValue(API_KEY_LENGTH));
        apiKey.setPrivateKey(valueGenerator.secureRandomValue(API_KEY_LENGTH));
        apiKey.setUser(user);

        return apiKeyRepository.save(apiKey);
    }
}
