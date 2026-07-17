package com.tripstack.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.tripstack.config.ConfigManager;

public final class DatabaseManager {

    private static final String DEFAULT_H2_URL = "jdbc:h2:mem:tripstack;DB_CLOSE_DELAY=-1";

    private DatabaseManager() {
    }

    public static Connection getConnection() throws SQLException {
        String host = ConfigManager.getOptional("DB_HOST");
        if (host == null || host.isBlank()) {
            return DriverManager.getConnection(DEFAULT_H2_URL, "sa", "");
        }

        int port = getPort();
        String database = ConfigManager.get("DB_NAME");
        String username = ConfigManager.get("DB_USER");
        String password = ConfigManager.get("DB_PASSWORD");

        String jdbcUrl = String.format("jdbc:postgresql://%s:%d/%s", host, port, database);
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    private static int getPort() {
        String portValue = ConfigManager.getOptional("DB_PORT");
        if (portValue == null || portValue.isBlank()) {
            return 5432;
        }
        return Integer.parseInt(portValue);
    }
}
