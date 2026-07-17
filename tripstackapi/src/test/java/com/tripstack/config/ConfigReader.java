package com.tripstack.config;

import io.github.cdimascio.dotenv.Dotenv;

public final class ConfigReader {

    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    private ConfigReader() {
    }

    public static String get(String key) {
        return dotenv.get(key);
    }

    public static String getBaseUrl() {
        return get("BASE_URL");
    }

    public static String getEmail() {
        return get("EMAIL");
    }

    public static String getPassword() {
        return get("PASSWORD");
    }

    public static String getMysqlImage() {
        return get("MYSQL_IMAGE");
    }

    public static String getDatabaseName() {
        return get("DB_NAME");
    }

    public static String getDatabaseUsername() {
        return get("DB_USERNAME");
    }

    public static String getDatabasePassword() {
        return get("DB_PASSWORD");
    }

    public static String getFrom() {
        return get("FROM");
    }

    public static String getTo() {
        return get("TO");
    }

    public static String getTravelDate() {
        return get("TRAVEL_DATE");
    }

    public static int getPassengers() {
        return Integer.parseInt(get("PASSENGERS"));
    }

    public static String getTravelClass() {
        return get("TRAVEL_CLASS");
    }

    public static String getEmployeeId() {
        return get("EMPLOYEE_ID");
    }

    public static String getExpectedPnrPrefix() {
        return get("EXPECTED_PNR_PREFIX");
    }

}