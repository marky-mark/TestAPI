package com.mtt.api.test.client.task.client.impl;

import com.mtt.api.model.MTTApiKey;
import com.mtt.api.test.client.TestUtils;
import com.mtt.api.test.client.base.client.BaseHttpClientTester;
import com.mtt.api.test.client.task.client.TaskTester;
import com.mtt.api.test.client.task.response.TaskResponseValidator;
import com.mtt.api.test.client.task.response.impl.TaskResponseValidatorImpl;
import com.mtt.api.test.exception.TestExecutionException;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.util.Assert;

public class HttpTaskApiClientTester extends BaseHttpClientTester implements TaskTester {

    MTTApiKey key;

    public HttpTaskApiClientTester(HttpHost host, DefaultHttpClient client) {
        super(host, client);

        key = new MTTApiKey();
        key.setAccessKey("testApiUser1AccessKey");
        key.setPrivateKey("testApiUser1PrivateKey");
    }

    /**------------->Create
     *
     * @return
     */
    @Override
    public TaskResponseValidator createTask() throws Exception {
        String createTaskPath = secureUrl("/api/task", key);
        StringEntity content;

        try {

            content = new StringEntity(requestBody.toString(), "UTF-8");
            content.setContentType("application/json");

            HttpPost createNewUser= new HttpPost(createTaskPath);
            createNewUser.setEntity(content);

            return new TaskResponseValidatorImpl(executeRequest(createNewUser));

        } catch (Exception ex) {
            throw new TestExecutionException("Failed to send request to API", ex);
        }
    }

    /**--------------->EDIT
     *
     * @param id
     * @return
     */
    @Override
    public TaskResponseValidator editTask(long id) throws Exception {
        Assert.notNull(id);
        String editTaskPath = secureUrl("/api/task/" + id, key);

        try {
            HttpPut editTask = new HttpPut(editTaskPath);
            return new TaskResponseValidatorImpl(executeRequest(editTask));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new TestExecutionException("Failed to send request to API", ex);
        }
    }

    /**-------------->Delete
     *
     * @param id
     * @return
     */
    @Override
    public TaskResponseValidator deleteTask(long id) throws Exception {
        Assert.notNull(id);
        String deleteTaskPath = secureUrl("/api/task/delete/" + id, key);

        try {
            HttpPost deleteTask = new HttpPost(deleteTaskPath);
            return new TaskResponseValidatorImpl(executeRequest(deleteTask));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new TestExecutionException("Failed to send request to API", ex);
        }
    }

    /**---------------->GET
     *
     * @param id
     * @return
     */
    @Override
    public TaskResponseValidator getTask(long id) throws Exception{
        Assert.notNull(id);

        String getTaskPath = secureUrl("/api/task/" + id, key);

        try {
            HttpGet getTask = new HttpGet(getTaskPath);
            return new TaskResponseValidatorImpl(executeRequest(getTask));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new TestExecutionException("Failed to send request to API", ex);
        }
    }

    //The Below is for CreateTaskRequest

    @Override
    public TaskTester withTitle(String title) {
        requestBody.put("title", title);
        return this;
    }

    @Override
    public TaskTester withDescription(String description) {
        requestBody.put("description", description);
        return this;
    }

    @Override
    public TaskTester withCheckedValue(boolean checked) {
        requestBody.put("checked", checked);
        return this;
    }

    @Override
    public TaskTester withUserId(long id) {
        requestBody.put("user_id", id);
        return this;
    }
}
