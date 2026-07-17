package db;

import config.SecretsManager;

public final class DatabaseConfig {

    private DatabaseConfig() {
        // Prevent object creation
    }

    public static String url() {
        return SecretsManager.get("DB_URL");
    }

    public static String username() {
        return SecretsManager.get("DB_USERNAME");
    }

    public static String password() {
        return SecretsManager.get("DB_PASSWORD");
    }

}