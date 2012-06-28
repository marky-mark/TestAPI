package com.mtt.api.test.steps.task;

import com.mtt.api.model.CreateTaskBean;
import com.mtt.api.model.request.CreateTaskRequest;
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

    //NOTE:: Alternatively can use restEasy :) ..see the APIClient for an example
    private TaskTester taskHttpClient;
    private TaskResponseValidator taskResponse;

    private Wiser wiser;

    private Long taskId;
    private CreateTaskRequest createTaskRequest;

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

        taskId = null;
        createTaskRequest = null;
        taskResponse = null;
    }

    @After("@task-fixture")
    public synchronized void cleanUpFixture() {

        taskFixture.cleanUp();

        if (wiser != null) {
            wiser.stop();
            wiser = null;
        }
    }

    @Given("^the user enters a task id of (.*?)$")
    public void enterTaskId(String taskId) {
        this.taskId = new Long(taskId);
    }

    @When("^the user tries to get the task$")
    public void getTask() throws Exception {
        taskResponse = taskHttpClient.getTask(taskId);
    }

    @Then("^the response status code should be (\\d+)$")
    public void responseCodeIs(Integer responseCode) {
        taskResponse.assertResponseStatusCode(responseCode);
    }

    @Then("^the (.*?) field should be \"(.*?)\"$")
    public void checkFieldValue(String field, String value) {
        taskResponse.assertField(field, value);
    }

    @Given("^the user wants to create a new task$")
    public void initialiseTask() {
        createTaskRequest = new CreateTaskRequest();
    }

    @Given("^the title field is set to \"(.*?)\"$")
    public void updateTitleTaskRequest(String value) {
        createTaskRequest.setTitle(value);
        taskHttpClient.withTitle(value);
    }

    @Given("^the description field is set to \"(.*?)\"$")
    public void updateDescriptionTaskRequest(String value) {
        createTaskRequest.setDescription(value);
        taskHttpClient.withDescription(value);
    }

    @Given("^the checked field is set to \"(.*?)\"$")
    public void updateCheckedTaskRequest(String checked) {
        boolean boolValue = Boolean.valueOf(checked);
        createTaskRequest.setChecked(boolValue);
        taskHttpClient.withCheckedValue(boolValue);
    }

    @Given("^the userId field is set to \"(.*?)\"$")
    public void updateUserIdTaskRequest(String value) {
        createTaskRequest.setUserId(Long.valueOf(value));
        taskHttpClient.withUserId(Long.valueOf(value));
    }

    @When("^the user tries to create the task$")
    public void createTask() throws Exception {
        taskResponse = taskHttpClient.createTask();
    }

    @Then("^the response should contain an error \"(.*?)\" for the (.*?) field$")
    public void checkError(String errorCode, String field) {
        taskResponse.assertBodyContainsFieldError(field);
        taskResponse.assertBodyContainsFieldError(field, errorCode);
    }

}


