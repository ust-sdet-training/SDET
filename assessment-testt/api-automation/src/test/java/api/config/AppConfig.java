package api.config;

import api.utils.PropertyReader;

public final class AppConfig {
    public static final String BASE_URL = PropertyReader.getProperty("API_BASE_URL", "https://api.tripstack.doomple.com");
    public static final String USER_EMAIL = PropertyReader.getProperty("API_USER_EMAIL", "xavier@tripstack.test");
    public static final String USER_PASSWORD = PropertyReader.getProperty("API_USER_PASSWORD", "Password@123");

    public static final String DB_URL = PropertyReader.getProperty("DB_URL", "");
    public static final String DB_USER = PropertyReader.getProperty("DB_USER", "");
    public static final String DB_PASSWORD = PropertyReader.getProperty("DB_PASSWORD", "");
    public static final boolean ENABLE_BOOKING = Boolean.parseBoolean(PropertyReader.getProperty("ENABLE_BOOKING", "true"));

    private AppConfig() {

    }
}
