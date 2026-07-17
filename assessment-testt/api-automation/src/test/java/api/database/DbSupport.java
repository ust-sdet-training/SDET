package api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DbSupport {

    private DbSupport() {
        // helper only
    }

    public static boolean isConfigured() {
        return !DbConfig.DB_URL.isBlank() && !DbConfig.DB_USER.isBlank() && !DbConfig.DB_PASSWORD.isBlank();
    }

    public static Connection getConnection() throws SQLException {
        if (!isConfigured()) {
            throw new IllegalStateException("DB credentials are not configured in application.properties");
        }
        return DriverManager.getConnection(DbConfig.DB_URL, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
    }
}
