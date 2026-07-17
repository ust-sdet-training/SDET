package org.sdet.config;

public class Secrets {

    public static String email() {
        return ConfigManager.getProperty("email");
    }

    public static String password() {
        return ConfigManager.getProperty("password");
    }

    public static String baseUrl() {
        return ConfigManager.getProperty("base.url");
    }

    public static String dbUrl() {
        return ConfigManager.getProperty("db.url");
    }

    public static String dbUsername() {
        return ConfigManager.getProperty("db.username");
    }

    public static String dbPassword() {
        return ConfigManager.getProperty("db.password");
    }

}