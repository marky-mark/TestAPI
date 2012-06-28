package com.mtt.api.test.client.base.response.impl;

import com.mtt.api.test.client.base.response.ResponseValidator;
import com.mtt.api.test.util.JsonUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.node.ArrayNode;
import org.hamcrest.Matchers;

import static com.mtt.api.test.util.RegexMatcher.matches;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

/**
 */
public class DefaultResponseValidator implements ResponseValidator {

    private HttpResponse response;

    private JsonNode responseBody;

    public DefaultResponseValidator(HttpResponse response) {
        this.response = response;
        this.responseBody = responseBodyAsJsonTree(response);
    }

    @Override
    public void assertHasBody() {
        assertThat(responseBody, Matchers.<Object>notNullValue());
    }

    @Override
    public void assertHasNoBody() {
        assertThat(responseBody, Matchers.<Object>nullValue());
    }

    @Override
    public void assertBodyContainsKey(String str) {
        assertThat("No body found in response. Status code was: " + response.getStatusLine().getStatusCode(),
                responseBody, Matchers.<Object>notNullValue());
        assertThat(responseBody.has(str), equalTo(true));
    }

    @Override
    public void assertResponseStatusCode(int statusCode) {
        assertThat(response.getStatusLine().getStatusCode(), equalTo(statusCode));
    }

    @Override
    public void assertResponseHeader(String headerName, String expectedRegEx) {
        String responseHeaderValue = null;
        Header header = response.getFirstHeader(headerName);
        if (header != null) {
            responseHeaderValue = header.getValue();
        }
        assertThat(responseHeaderValue, matches(expectedRegEx));
    }

    @Override
    public void assertBodyContainsErrorCode(String errorCode) {
        JsonNode errorCodeNode = responseBody.get("error_code");
        assertThat(errorCodeNode.getValueAsText(), equalTo(errorCode));
    }

    @Override
    public void assertBodyContainsFieldError(String fieldName) {
        boolean foundError = false;
        JsonNode node = responseBody.get("errors");
        assertThat(node, instanceOf(ArrayNode.class));
        ArrayNode errorsNode = (ArrayNode) node;
        for (JsonNode errorNode : errorsNode) {
            JsonNode fieldNameNode = errorNode.findPath("field");
            if (fieldNameNode.getValueAsText().equals(fieldName)) {
                foundError = true;
                break;
            }
        }
        assertThat(foundError, equalTo(true));
    }

    @Override
    public void assertBodyContainsMessageCode(String messageCode) {
        boolean foundCode = false;
        JsonNode node = responseBody.get("errors");
        assertThat(node, instanceOf(ArrayNode.class));
        ArrayNode errorsNode = (ArrayNode) node;
        for (JsonNode errorNode : errorsNode) {
            JsonNode messageCodeNode = errorNode.findPath("message_code");
            if (messageCodeNode.getValueAsText().equals(messageCode)) {
                foundCode = true;
                break;
            }
        }
        assertThat(foundCode, equalTo(true));
    }

    protected HttpResponse getResponse() {
        return response;
    }

    protected JsonNode getResponseBody() {
        return responseBody;
    }

    private JsonNode responseBodyAsJsonTree(HttpResponse response) {
        try {
/*            BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String s;
            while ((s = r.readLine()) != null) {
                System.out.println("JSON:"+s);
            }
            return null;
*/
            HttpEntity entity = response.getEntity();
            JsonParser jp = JsonUtils.jsonFactory.createJsonParser(entity.getContent());
            return JsonUtils.mapper.readTree(jp);
        } catch (Exception ex) {
            return null;
        }
    }
}
