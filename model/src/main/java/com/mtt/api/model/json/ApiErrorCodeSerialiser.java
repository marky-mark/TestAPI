package com.mtt.api.model.json;

import com.mtt.api.model.error.ApiErrorCode;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

/**
 * For serialising a {@link com.mtt.api.model.error.ApiErrorCode} to JSON wire format
 * <p/>
 * TODO: Unit tests
 */
public final class ApiErrorCodeSerialiser extends JsonSerializer<ApiErrorCode> {

    /**
     * Serialize ApiErrorCode to JSON string
     * @param value error code
     * @param jgen json generator
     * @param provider serializer
     * @throws java.io.IOException - possible
     * @throws org.codehaus.jackson.JsonGenerationException - possible
     */
    public void serialize(ApiErrorCode value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonGenerationException {
        jgen.writeString(value.asString());
    }
}
