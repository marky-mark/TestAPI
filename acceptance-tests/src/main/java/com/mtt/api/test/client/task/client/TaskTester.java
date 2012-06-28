package com.mtt.api.test.client.task.client;

import com.mtt.api.test.client.task.response.TaskResponseValidator;

public interface TaskTester {

    TaskResponseValidator createTask() throws Exception;

    TaskResponseValidator editTask(long id) throws Exception;

    TaskResponseValidator deleteTask(long id) throws Exception;

    TaskResponseValidator getTask(long id) throws Exception;

    TaskTester withTitle(String title);

    TaskTester withDescription(String description);

    TaskTester withCheckedValue(boolean checked);

    TaskTester withUserId(long id);
}
