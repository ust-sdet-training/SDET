package api.database;

import api.utils.PropertyReader;

public final class DbConfig {
    private DbConfig() {
    }

    public static String getDbUrl() {
        return PropertyReader.getProperty("DB_URL", "");
    }

    public static String getDbUser() {
        return PropertyReader.getProperty("DB_USER", "");
    }

    public static String getDbPassword() {
        return PropertyReader.getProperty("DB_PASSWORD", "");
    }
}
