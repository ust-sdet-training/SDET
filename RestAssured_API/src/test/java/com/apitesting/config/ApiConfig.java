package com.apitesting.config;

public class ApiConfig {
    public static final String BASE_URL = getenv("TRIPSTACK_API_BASE_URL", "https://tripstack.doomple.com");
    public static final String EMAIL = getenv("TRIPSTACK_API_EMAIL", "bianca@tripstack.test");
    public static final String PASSWORD = getenv("TRIPSTACK_API_PASSWORD", "Password@123");
    public static final String VIEWER_EMAIL = getenv("TRIPSTACK_VIEWER_EMAIL", "bob@tripstack.test");
    public static final String VIEWER_PASSWORD = getenv("TRIPSTACK_VIEWER_PASSWORD", "Password@123");
    public static final String ORIGIN = getenv("TRIPSTACK_ORIGIN", "DEL");
    public static final String DESTINATION = getenv("TRIPSTACK_DESTINATION", "COK");
    public static final int DAYS_AHEAD = Integer.parseInt(getenv("TRIPSTACK_DAYS_AHEAD", "29"));

    private static String getenv(String name, String fallback) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? fallback : value;
    }
}
