package com.week7.finalgate.DB.config;

public final class DatabaseManager {

    private static boolean initialized = false;

    private DatabaseManager() {
    }

    public static synchronized void initialize() {

        if (initialized) {
            return;
        }

        // Start Testcontainers here if needed
        // Run DatabaseInitializer here if needed

        initialized = true;
    }
}