package com.assessment.ui.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private final Properties properties;

    public ConfigReader() {
        properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {

            if (inputStream == null) {
                throw new IllegalStateException("config.properties was not found on the test classpath.");
            }

            properties.load(inputStream);

        } catch (IOException e) {
            throw new RuntimeException("Could not load config.properties", e);
        }
    }

    private String getProperty(String key) {
        return System.getProperty(key, properties.getProperty(key));
    }

    public String getBaseUrl() {
        return getProperty("app.base.url");
    }

    public String getUsername() {
        return getProperty("app.username");
    }

    public String getPassword() {
        return getProperty("app.password");
    }
    public boolean isHeadless() {
    return Boolean.parseBoolean(
            System.getProperty("headless",
                    properties.getProperty("headless")));
}
}