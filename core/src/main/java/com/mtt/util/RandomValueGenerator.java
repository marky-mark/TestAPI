package com.mtt.util;

/**
 * Service for creating values.
 */
public interface RandomValueGenerator {

    /**
     * Create a secure random value of the given length.
     *
     * @param length the number of characters
     * @return a random value of the given length.
     */
    String secureRandomValue(int length);
}
