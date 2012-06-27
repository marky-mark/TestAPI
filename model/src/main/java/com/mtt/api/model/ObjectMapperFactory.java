package com.mtt.api.model;

import com.mtt.api.model.json.DateTimeDeserialiser;
import com.mtt.api.model.json.DateTimeSerialiser;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.module.SimpleModule;
import org.joda.time.DateTime;

public final class ObjectMapperFactory {

    private final PropertyNamingStrategy propertyNamingStrategy;

    public ObjectMapperFactory() {
        propertyNamingStrategy = new SnakeCasePropertyNamingStrategy();
    }

    public ObjectMapperFactory(PropertyNamingStrategy propertyNamingStrategy) {
        this.propertyNamingStrategy = propertyNamingStrategy;
    }

    public ObjectMapper create() {

        SimpleModule module = new SimpleModule("MTT", new Version(0, 0, 0, ""));

        //Serialize and deserilize DateTime
        module.addSerializer(DateTime.class, new DateTimeSerialiser());
        module.addDeserializer(DateTime.class, new DateTimeDeserialiser());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(propertyNamingStrategy);
        objectMapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        objectMapper.registerModule(module);

        return objectMapper;
    }
}
