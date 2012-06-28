package com.mtt.api.test.util;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;

/**
 */
public final class JsonUtils {

    public static ObjectMapper mapper = new ObjectMapper();

    public static JsonFactory jsonFactory = mapper.getJsonFactory();
}
