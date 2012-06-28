package com.mtt.api.test.client.task.response;

import com.mtt.api.test.client.base.response.ResponseValidator;
import org.joda.time.DateTime;

public interface TaskResponseValidator extends ResponseValidator {

//    long getId();
//
//    long getUserId();
//
//    boolean isChecked();
//
//    String getTitle();
//
//    String getDescription();

    void assertId(long id);

    void assertUserEmail(String email);

    void assertTitle(String title);

    void assertDescription(String description);

    void assertIsChecked(boolean isChecked);

    void assertCreatedDate(String dateTime);
}
