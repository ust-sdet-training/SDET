package com.routepulse.api.config;

public class TravelConfigManager {
    private static TravelConfigManager instance;

    private final String baseUrl;
    private final String email;
    private final String password;
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;
    private final String from;
    private final String to;
    private final int travelOffsetDays;

    private TravelConfigManager() {
        this.baseUrl = readValue("TRIPSTACK_BASE_URL", "https://tripstack.doomple.com");
        this.email = readValue("TRIPSTACK_EMAIL", "trent@tripstack.test");
        this.password = readValue("TRIPSTACK_PASSWORD", "Password@123");
        this.dbUrl = readValue("TRIPSTACK_DB_URL", "jdbc:postgresql://localhost:5432/tripstack");
        this.dbUser = readValue("TRIPSTACK_DB_USER", "postgres");
        this.dbPassword = readValue("TRIPSTACK_DB_PASSWORD", "password");
        this.from = readValue("TRIPSTACK_FROM", "HYD");
        this.to = readValue("TRIPSTACK_TO", "DEL");
        this.travelOffsetDays = readIntValue("TRIPSTACK_TRAVEL_OFFSET_DAYS", 19);
    }

    public static TravelConfigManager getInstance() {
        if (instance == null) {
            instance = new TravelConfigManager();
        }
        return instance;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getTravelOffsetDays() {
        return travelOffsetDays;
    }

    private String readValue(String key, String defaultValue) {
        String systemProperty = System.getProperty(key);
        if (systemProperty != null && !systemProperty.isBlank()) {
            return systemProperty;
        }

        String envValue = System.getenv(key);
        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        return defaultValue;
    }

    private int readIntValue(String key, int defaultValue) {
        String value = readValue(key, String.valueOf(defaultValue));
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
