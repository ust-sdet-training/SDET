package com.capstone.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static final Properties properties = new Properties();

    static {

        String environment = System.getProperty("env", "local");
        String fileName = "config/application-" + environment + ".properties";

        try (InputStream input = AppConfig.class
                .getClassLoader()
                .getResourceAsStream(fileName)) {

            if (input == null) {
                throw new RuntimeException(fileName + " not found");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load " + fileName, e);
        }
    }

    private AppConfig() {
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}
