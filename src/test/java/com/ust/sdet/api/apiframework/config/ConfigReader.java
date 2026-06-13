package com.ust.sdet.api.apiframework.config;

import io.github.cdimascio.dotenv.Dotenv;

public class ConfigReader {

    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    public static String get(String key, String fallback) {

        String systemValue = System.getProperty(key);

        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }

        String envValue = dotenv.get(key);

        return envValue == null || envValue.isBlank()
                ? fallback
                : envValue;
    }
    public static String get(String key) {
        return get(key, null);
    }

}