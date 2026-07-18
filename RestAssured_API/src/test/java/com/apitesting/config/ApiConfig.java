package com.apitesting.config;

import io.github.cdimascio.dotenv.Dotenv;

public class ApiConfig {
    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    public static final String BASE_URL = require("SANKARAN_API_BASE_URL");
    public static final String EMAIL = require("SANKARAN_API_EMAIL");
    public static final String PASSWORD = require("SANKARAN_API_PASSWORD");
    public static final String VIEWER_EMAIL = require("SANKARAN_VIEWER_EMAIL");
    public static final String VIEWER_PASSWORD = require("SANKARAN_VIEWER_PASSWORD");
    public static final String EMP_ID = require("SANKARAN_EMP_ID");
    public static final String ORIGIN = require("SANKARAN_ORIGIN");
    public static final String DESTINATION = require("SANKARAN_DESTINATION");
    public static final int DAYS_AHEAD = Integer.parseInt(require("SANKARAN_DAYS_AHEAD"));

    private static String require(String name) {
        String value = dotenv.get(name);
        if (value == null || value.isBlank()) {
            String systemValue = System.getenv(name);
            if (systemValue != null && !systemValue.isBlank()) {
                value = systemValue;
            }
        }

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Missing required env var: " + name + " (set it in .env locally or as a repo secret in CI)");
        }
        return value;
    }
}