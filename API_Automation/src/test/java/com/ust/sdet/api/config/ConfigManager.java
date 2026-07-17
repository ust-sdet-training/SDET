package com.ust.sdet.api.config;

public class ConfigManager {
    private static ConfigManager instance;

    private final String baseUrl;
    private final String email;
    private final String password;
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;
    private final int empId;

    private ConfigManager() {
        this.baseUrl = readValue("TRIPSTACK_BASE_URL", "https://tripstack.doomple.com");
        this.email = readValue("TRIPSTACK_EMAIL", "erin@tripstack.test");
        this.password = readValue("TRIPSTACK_PASSWORD", "Password@123");
        this.dbUrl = readValue("TRIPSTACK_DB_URL", "jdbc:postgresql://localhost:5432/tripstack");
        this.dbUser = readValue("TRIPSTACK_DB_USER", "postgres");
        this.dbPassword = readValue("TRIPSTACK_DB_PASSWORD", "password");
        this.empId = 1005;
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
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

    public int getEmpId() {
        return empId;
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
}
