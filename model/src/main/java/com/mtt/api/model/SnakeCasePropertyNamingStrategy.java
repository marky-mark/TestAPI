package com.mtt.api.model;

import org.codehaus.jackson.map.MapperConfig;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.codehaus.jackson.map.introspect.AnnotatedField;
import org.codehaus.jackson.map.introspect.AnnotatedMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * A Jackson Naming Strategy that implements snake-case naming for keys in JSON serialisation.
 *
 * Snake-case naming examples:
 *  Camel-case: anAdvertId
 *  Snake-case: an_advert_id
 *
 *  Camel-case: SnakeCasePropertyNamingStrategy
 *  Snake-case: snake_case_property_naming_strategy
 *
 * @author nrantor
 */
public class SnakeCasePropertyNamingStrategy
    extends PropertyNamingStrategy {

    private static final Map<String, String> CACHE = new HashMap<String, String>();

    @Override
    public final String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
        return toSnakeCase(defaultName);
    }

    @Override
    public final String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
        return toSnakeCase(defaultName);
    }

    @Override
    public final String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
        return toSnakeCase(defaultName);
    }

    private String toSnakeCase(String string) {
        if (!CACHE.containsKey(string)) {
            char[] chars = string.toCharArray();
            StringBuilder buf = new StringBuilder(chars.length * 2);
            for (char character : chars) {
                if (Character.isUpperCase(character)) {
                    buf.append('_');
                    buf.append(character);
                } else {
                    buf.append(character);
                }
            }
            CACHE.put(string, buf.toString().toLowerCase());
        }
        return CACHE.get(string);
    }
}
