package utils;

import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties properties =
            new Properties();

    static {

        try {

            InputStream file =
                    ConfigReader.class
                            .getClassLoader()
                            .getResourceAsStream(
                                    "config.properties");

            if (file == null) {

                throw new RuntimeException(
                        "config.properties file not found");
            }

            properties.load(file);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to load config.properties",
                    e);
        }
    }

    private ConfigReader() {
    }

    // Base URL

    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    // Database

    public static String getDbUrl() {
        return properties.getProperty("db.url");
    }

    public static String getDbUsername() {
        return properties.getProperty("db.username");
    }

    public static String getDbPassword() {
        return properties.getProperty("db.password");
    }

    // OAuth OPS Client

    public static String getOpsClientId() {
        return properties.getProperty(
                "oauth.ops.client.id");
    }

    public static String getOpsClientSecret() {
        return properties.getProperty(
                "oauth.ops.client.secret");
    }

    // OAuth Viewer Client

    public static String getViewerClientId() {
        return properties.getProperty(
                "oauth.viewer.client.id");
    }

    public static String getViewerClientSecret() {
        return properties.getProperty(
                "oauth.viewer.client.secret");
    }

    // OAuth Expired Client

    public static String getExpiredClientId() {
        return properties.getProperty(
                "oauth.expired.client.id");
    }

    public static String getExpiredClientSecret() {
        return properties.getProperty(
                "oauth.expired.client.secret");
    }

    // Customer Login

    public static String getCustomerEmail() {
        return properties.getProperty(
                "customer.email");
    }

    public static String getCustomerPassword() {
        return properties.getProperty(
                "customer.password");
    }

    // API Key

    public static String getApiKey() {
        return properties.getProperty(
                "api.key");
    }
}