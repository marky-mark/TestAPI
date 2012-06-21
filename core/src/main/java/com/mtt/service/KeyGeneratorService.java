package com.mtt.service;

/**
 * Service for helping to create "secure" keys.
 */
public interface KeyGeneratorService {

    /**
     * Generate a key based on the given input data.
     *
     * @param inputData some input data to include in the key generation process
     * @return a key based on the given input data.
     */
    String generateKey(String inputData);
}
