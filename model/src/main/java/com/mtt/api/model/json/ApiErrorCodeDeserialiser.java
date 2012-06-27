package com.mtt.api.model.json;

import com.mtt.api.model.error.ApiErrorCode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

/**
 * For deserialising JSON String format for a {@link ApiErrorCode}.
 * <p/>
 * TODO: Unit tests
 */
public final class ApiErrorCodeDeserialiser extends JsonDeserializer<ApiErrorCode> {

    /**
     * deserialize ApiErrorCode from String
     * @param jp - JsonParser
     * @param ctx - context
     * @return ApiErrorCode
     * @throws java.io.IOException - possible
     * @throws org.codehaus.jackson.JsonProcessingException - possible
     */
    public ApiErrorCode deserialize(JsonParser jp, DeserializationContext ctx)
            throws IOException, JsonProcessingException {
        String s = jp.getText();
        try {
            return ApiErrorCode.fromString(s);
        } catch (Exception e) {
            throw new JsonParseException("Cannot unmarshall string " + s
                        + " as an attribute search type value", jp.getTokenLocation());
        }
    }
}
