package com.travel.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    public static void loadConfig() {

        String env = System.getProperty("env", "local");

        properties = new Properties();

        try (FileInputStream fis = new FileInputStream(
                "src/test/resources/config/config.properties")) {

            properties.load(fis);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
