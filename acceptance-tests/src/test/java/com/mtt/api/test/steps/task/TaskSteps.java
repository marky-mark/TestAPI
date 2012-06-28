package com.mtt.api.test.steps.task;

import com.mtt.api.test.client.task.client.TaskTester;
import com.mtt.api.test.client.task.client.impl.HttpTaskApiClientTester;
import com.mtt.api.test.client.task.response.TaskResponseValidator;
import com.mtt.fixtures.Fixture;
import cucumber.annotation.After;
import cucumber.annotation.Before;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.subethamail.wiser.Wiser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TaskSteps {

    private TaskTester taskHttpClient;
    private TaskResponseValidator taskResponse;

    private Wiser wiser;

    private Long taskId;

    @Autowired
    @Qualifier("taskFixture")
    private Fixture taskFixture;

    @Autowired
    private HttpHost apiHost;

    @Before("@task-fixture")
    public synchronized void loadFixture() {

        taskFixture.run();

        wiser = new Wiser();
        wiser.setPort(2500);
        wiser.start();

        taskHttpClient = new HttpTaskApiClientTester(apiHost, new DefaultHttpClient(new ThreadSafeClientConnManager()));
    }

    @After("@task-fixture")
    public synchronized void cleanUpFixture() {

        taskFixture.cleanUp();

        if (wiser != null) {
            wiser.stop();
            wiser = null;
        }

        taskId = null;
    }

    @Given("^the user enters a task id of (.*?)$")
    public void enterTaskId(String taskId) {
        this.taskId = new Long(taskId);
    }

    @When("^the user tries to get the task$")
    public void getTask() {
        taskResponse = taskHttpClient.getTask(taskId);
    }

    @Then("^the response status code should be (\\d+)$")
    public void responseCodeIs(Integer responseCode) {
        taskResponse.assertResponseStatusCode(responseCode);
    }

}


