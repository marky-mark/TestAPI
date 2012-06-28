package com.mtt.api.test.client.task.response.impl;


import com.mtt.api.test.client.base.response.impl.DefaultResponseValidator;
import com.mtt.api.test.client.task.response.TaskResponseValidator;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.JsonNode;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class TaskResponseValidatorImpl extends DefaultResponseValidator implements TaskResponseValidator {

    private static final String TITLE_FIELD = "title";
    private static final String ID_FIELD = "id";
    private static final String CHECKED_FIELD = "checked";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String CREATED_DATE_FIELD = "created_date";
    private static final String USER_EMAIL_FIELD = "user_email";

    public TaskResponseValidatorImpl(HttpResponse httpResponse) {
        super(httpResponse);
    }

    @Override
    public void assertId(long id) {
        JsonNode statusNode = getResponseBody().get(ID_FIELD);
        assertThat(statusNode, notNullValue());
        assertThat(statusNode.getLongValue(), equalTo(id));
    }

    @Override
    public void assertUserEmail(String email) {
        JsonNode statusNode = getResponseBody().get(USER_EMAIL_FIELD);
        assertThat(statusNode, notNullValue());
        assertThat(statusNode.getTextValue(), equalTo(email));
    }

    @Override
    public void assertTitle(String title) {
        JsonNode statusNode = getResponseBody().get(TITLE_FIELD);
        assertThat(statusNode, notNullValue());
        assertThat(statusNode.getTextValue(), equalTo(title));
    }

    @Override
    public void assertDescription(String description) {
        JsonNode statusNode = getResponseBody().get(DESCRIPTION_FIELD);
        assertThat(statusNode, notNullValue());
        assertThat(statusNode.getTextValue(), equalTo(description));
    }

    @Override
    public void assertIsChecked(boolean isChecked) {
        JsonNode statusNode = getResponseBody().get(CHECKED_FIELD);
        assertThat(statusNode, notNullValue());
        assertThat(statusNode.getBooleanValue(), equalTo(isChecked));
    }

    @Override
    public void assertCreatedDate(String dateTime) {
        JsonNode statusNode = getResponseBody().get(CREATED_DATE_FIELD);
        assertThat(statusNode, notNullValue());
        assertThat(statusNode.getTextValue(), equalTo(dateTime));
    }

    @Override
    public void assertField(String field, String value) {
        JsonNode statusNode = getResponseBody().get(field);
        assertThat(statusNode, notNullValue());

        if (value.equals("false") || value.equals("true"))
            assertThat(statusNode.getBooleanValue(), equalTo(new Boolean(value)));
        else
            assertThat(statusNode.getTextValue(), equalTo(value));
    }
}
