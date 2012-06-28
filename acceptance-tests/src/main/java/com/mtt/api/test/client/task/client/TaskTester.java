package com.mtt.api.test.client.task.client;

import com.mtt.api.test.client.task.response.TaskResponseValidator;

public interface TaskTester {

    TaskResponseValidator createTask();

    TaskResponseValidator editTask(long id);

    TaskResponseValidator deleteTask(long id);

    TaskResponseValidator getTask(long id);

    TaskTester withTitle(String title);

    TaskTester withDescription(String description);

    TaskTester withCheckedValue(boolean checked);

    TaskTester withUserId(long id);
}
