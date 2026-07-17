package com.ust.sdet.api.db.config;

import com.ust.sdet.api.config.ConfigManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;
    private static ConfigManager configManager = ConfigManager.getInstance();

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL Driver not found. Database tests will skip.");
            System.out.println("Add 'org.postgresql:postgresql' dependency to pom.xml to enable DB tests");
        }
    }
    public static Connection getConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    return connection;
                }
            } catch (SQLException e) {
                System.out.println("Connection validation failed: " + e.getMessage());
            }
        }

        try {
            connection = DriverManager.getConnection(
                    configManager.getDbUrl(),
                    configManager.getDbUser(),
                    configManager.getDbPassword()
            );
            System.out.println("Database connection established");
            return connection;
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            System.out.println("Ensure TRIPSTACK_DB_URL, TRIPSTACK_DB_USER, TRIPSTACK_DB_PASSWORD env vars are set");
            System.out.println("Database tests will be skipped. Run with: -DTRIPSTACK_DB_URL=jdbc:postgresql://host:5432/db");
            return null;
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed");
                connection = null;
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
