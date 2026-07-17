package com.tripstack.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public final class ConfigManager {

    private static final Properties FALLBACK_PROPERTIES = loadFallbackProperties();

    private ConfigManager() {
    }

    public static String get(String key) {
        String value = getOptional(key);
        if (Objects.isNull(value) || value.isBlank()) {
            throw new IllegalStateException("Missing configuration for key: " + key);
        }
        return value.trim();
    }

    public static String getOptional(String key) {
        String value = System.getenv(key);
        if (value != null && !value.isBlank()) {
            return value.trim();
        }

        value = System.getProperty(key);
        if (value != null && !value.isBlank()) {
            return value.trim();
        }

        value = FALLBACK_PROPERTIES.getProperty(key);
        if (value != null && !value.isBlank()) {
            return value.trim();
        }

        String normalizedKey = normalizeKey(key);
        value = FALLBACK_PROPERTIES.getProperty(normalizedKey);
        return value != null ? value.trim() : null;
    }

    private static String normalizeKey(String key) {
        return key.toLowerCase().replace('_', '.');
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    private static Properties loadFallbackProperties() {
        Properties properties = new Properties();
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to load config.properties", e);
        }
        return properties;
    }
}
