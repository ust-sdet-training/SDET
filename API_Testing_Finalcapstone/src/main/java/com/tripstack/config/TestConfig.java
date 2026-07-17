package com.tripstack.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {
    private static final Properties PROPERTIES = loadProperties();
    private static String baseUrlOverride;

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = TestConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to load config.properties", e);
        }
        return properties;
    }

    public static String getBaseUrl() {
        return baseUrlOverride != null ? baseUrlOverride : getProperty("base.url", "http://localhost:8080");
    }

    public static void setBaseUrl(String baseUrl) {
        baseUrlOverride = baseUrl;
    }

    public static String getDbUrl() {
        return getProperty("db.url", "jdbc:postgresql://localhost:5432/tripstack");
    }

    public static String getDbUser() {
        return getProperty("db.user", "tripstack");
    }

    public static String getDbPassword() {
        return getProperty("db.password", "tripstack");
    }

    private static String getProperty(String key, String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }
}
