package com.travel.utils;

import io.github.cdimascio.dotenv.Dotenv;

public final class EnvReader {

    private static final Dotenv dotenv =
            Dotenv.configure()
                    .ignoreIfMissing()
                    .load();

    private EnvReader() {
    }

    public static String get(String key) {
        String value = System.getenv(key);

        if (value == null || value.isBlank()) {
            value = dotenv.get(key);
        }

        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing environment variable: " + key);
        }

        return value;
    }
}
