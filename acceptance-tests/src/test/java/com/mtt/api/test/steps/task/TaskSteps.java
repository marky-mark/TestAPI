package com.mtt.api.test.steps.task;

import com.mtt.fixtures.Fixture;
import cucumber.annotation.After;
import cucumber.annotation.Before;
import org.apache.http.HttpHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.subethamail.wiser.Wiser;

public class TaskSteps {

    private Wiser wiser;

    @Autowired
    @Qualifier("taskFixture")
    private Fixture taskFixture;

    @Autowired
    private HttpHost apiHost;

    @Before("@user-activation-fixture")
    public synchronized void loadFixture() {
        taskFixture.run();
        wiser = new Wiser();
        wiser.setPort(2500);
        wiser.start();
    }

    @After("@user-activation-fixture")
    public synchronized void cleanUpFixture() {
        taskFixture.cleanUp();
        if (wiser != null) {
            wiser.stop();
            wiser = null;
        }
    }
}


