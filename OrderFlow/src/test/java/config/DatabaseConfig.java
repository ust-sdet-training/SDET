package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    public static Connection getConnection() {

        try {

            return DriverManager.getConnection(
                    ConfigReader.DB_URL,
                    ConfigReader.DB_USERNAME,
                    ConfigReader.DB_PASSWORD
            );

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Database connection failed",
                    e
            );
        }
    }
}