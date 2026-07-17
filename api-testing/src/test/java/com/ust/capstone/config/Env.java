package com.ust.capstone.config;

import io.github.cdimascio.dotenv.Dotenv;

public final class Env {

    private static final Dotenv DOTENV = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    private Env() {
    }

    public static String get(String key) {

        String value = System.getenv(key);

        if (value == null || value.isBlank()) {
            value = System.getProperty(key);
        }

        if (value == null || value.isBlank()) {
            value = DOTENV.get(key);
        }

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    "Missing environment variable: " + key
            );
        }

        return value;
    }
}