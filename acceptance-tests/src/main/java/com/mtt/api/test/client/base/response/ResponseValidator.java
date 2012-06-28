package com.mtt.api.test.client.base.response;

/**
 */
public interface ResponseValidator {

    void assertHasBody();

    void assertHasNoBody();

    void assertBodyContainsKey(String str);

    void assertResponseStatusCode(int statusCode);

    void assertResponseHeader(String headerName, String regEx);

    void assertBodyContainsErrorCode(String errorCode);

    void assertBodyContainsFieldError(String fieldName);

    void assertBodyContainsMessageCode(String messageCode);
}
