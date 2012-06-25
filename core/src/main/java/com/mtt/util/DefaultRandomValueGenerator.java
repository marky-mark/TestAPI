package com.mtt.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.stereotype.Component;

/**
 * Default implementation of {@link RandomValueGenerator}.
 */
@Component
public final class DefaultRandomValueGenerator implements RandomValueGenerator {

    @Override
    public String secureRandomValue(int length) {
        return new SecureRandomNumberGenerator().nextBytes(length).toHex().substring(0, length);
    }
}
