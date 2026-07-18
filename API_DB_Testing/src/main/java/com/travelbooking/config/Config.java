package com.travelbooking.config;

import io.github.cdimascio.dotenv.Dotenv;

public final class Config {

    private static final Dotenv DOTENV = Dotenv.configure()
            .ignoreIfMalformed()
            .ignoreIfMissing()
            .load();

    private Config() {
    }

    public static String get(String key) {

        String value = System.getenv(key);

        if (value == null || value.isBlank()) {
            value = DOTENV.get(key);
        }

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing configuration: " + key);
        }

        return value;
    }

    public static String get(String key, String defaultValue) {

        String value = System.getenv(key);

        if (value == null || value.isBlank()) {
            value = DOTENV.get(key);
        }

        return (value == null || value.isBlank()) ? defaultValue : value;
    }

    public static final String BASE_URL =
            get("BASE_URL", "https://api.tripstack.doomple.com");

}