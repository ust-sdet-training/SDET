package com.Capstone.api.config;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();
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
                    "Missing env data");
        }
        return value;
    }
    public static final String BASE_URL = require("API_BASE_URL");
    public static final String EMAIL = require("API_EMAIL");
    public static final String PASSWORD = require("API_PASSWORD");
    public static final String VIEWER_EMAIL = require("VIEWER_EMAIL");
    public static final String VIEWER_PASSWORD = require("VIEWER_PASSWORD");
    public static final String EMP_ID = require("EMP_ID");
    public static final String ORIGIN = require("ORIGIN");
    public static final String DESTINATION = require("DESTINATION");
    public static final int DAYS_AHEAD = Integer.parseInt(require("DAYS_AHEAD"));
}
