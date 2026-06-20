package com.ust.sdet.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException(
                        "config.properties not found");
            }
            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Unable to load config.properties", e);
        }
    }

    private Config() {
    }

    public static String browser() {
        return properties.getProperty("browser");
    }

    public static boolean headless() {
        return Boolean.parseBoolean(properties.getProperty("headless"));
    }

    public static String baseUrl() {
        return properties.getProperty("baseUrl");
    }

    public static int timeout() {
        return Integer.parseInt(
                properties.getProperty("timeout"));
    }
}