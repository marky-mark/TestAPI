package com.mtt.api.test.client.task.response;

import com.mtt.api.test.client.base.response.ResponseValidator;
import org.joda.time.DateTime;

public interface TaskResponseValidator extends ResponseValidator {

    void assertId(long id);

    void assertUserEmail(String email);

    void assertTitle(String title);

    void assertDescription(String description);

    void assertIsChecked(boolean isChecked);

    void assertCreatedDate(String dateTime);

    void assertField(String field, String value);
}
