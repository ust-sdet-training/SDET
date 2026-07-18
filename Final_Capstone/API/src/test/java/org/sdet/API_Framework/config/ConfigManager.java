package org.sdet.API_Framework.config;

public final class ConfigManager {

    private ConfigManager() {
    }

    public static String getBaseUrl() {
        return Secrets.BASE_URL;
    }

    public static String getEmail() {
        return Secrets.EMAIL;
    }

    public static String getPassword() {
        return Secrets.PASSWORD;
    }

    public static String getDbUrl() {
        return Secrets.DB_URL;
    }

    public static String getDbUsername() {
        return Secrets.DB_USERNAME;
    }

    public static String getDbPassword() {
        return Secrets.DB_PASSWORD;
    }

}