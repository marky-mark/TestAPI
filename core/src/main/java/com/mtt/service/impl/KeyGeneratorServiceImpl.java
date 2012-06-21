package com.mtt.service.impl;

import com.mtt.service.KeyGeneratorService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link com.mtt.service.KeyGeneratorService}.
 */
@Service
public final class KeyGeneratorServiceImpl implements KeyGeneratorService {

    private final String salt = "mttKeyGenSalt";

    @Override
    public String generateKey(String inputData) {
        return new Sha256Hash(inputData, salt, 1024).toHex();
    }
}
