package com.mtt.api.client.util;

import com.mtt.api.model.ObjectMapperFactory;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * JAX-RS {@link javax.ws.rs.ext.ContextResolver} for registering a custom Jackson {@link org.codehaus.jackson.map.ObjectMapper}.
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public final class JacksonContextResolver implements ContextResolver<ObjectMapper> {

    private ObjectMapper objectMapper;

    /**
     * Constructor.
     */
    public JacksonContextResolver() {
        this.objectMapper = new ObjectMapperFactory().create();
    }

    /**
     * Get the {@link org.codehaus.jackson.map.ObjectMapper}
     *
     * @param objectType object type
     * @return the {@link org.codehaus.jackson.map.ObjectMapper}
     */
    public ObjectMapper getContext(Class<?> objectType) {
        return objectMapper;
    }
}