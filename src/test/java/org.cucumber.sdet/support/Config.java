package org.cucumber.sdet.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input =
                     Config.class.getClassLoader()
                             .getResourceAsStream("env.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "env.properties not found in resources folder"
                );
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to load env.properties",
                    e
            );
        }
    }

    private Config() {
    }

    public static String baseUrl() {
        return properties.getProperty("baseUrl")
                .replaceAll("/$", "");
    }

    public static String catalogUrl() {
        return baseUrl() + "/catalog";
    }

    public static boolean headless() {
        return Boolean.parseBoolean(
                properties.getProperty("headless")
        );
    }

    public static int timeout() {
        return Integer.parseInt(
                properties.getProperty("timeout")
        );
    }

    public static String windowWidth() {
        return properties.getProperty("windowWidth");
    }

    public static String windowHeight() {
        return properties.getProperty("windowHeight");
    }
}
