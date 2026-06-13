package com.ust.sdet.api.apiframework.support;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public final class FixtureLoader {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private FixtureLoader() {}

    public static Map<String, Object> loadJsonAsMap(String resourcePath) {
        try (InputStream is = FixtureLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }
            return MAPPER.readValue(is, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Unable to read fixture: " + resourcePath, e);
        }
    }
}
