package com.mtt.fixtures;

/**
 * For setting up the system under test prior to test execution.
 */
public interface Fixture {

    /**
     * Execute fixture.
     */
    void run();

    /**
     * Clean up fixture.
     */
    void cleanUp();
}
