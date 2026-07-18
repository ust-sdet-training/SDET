package api.database;

import api.utils.PropertyReader;

public final class DbConfig {
    public static final String DB_URL = PropertyReader.getProperty("DB_URL", "");
    public static final String DB_USER = PropertyReader.getProperty("DB_USER", "");
    public static final String DB_PASSWORD = PropertyReader.getProperty("DB_PASSWORD", "");

    private DbConfig() {
    }
}
