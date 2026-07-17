package org.sdet.database;

import org.sdet.config.Secrets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(
                    Secrets.dbUrl(),
                    Secrets.dbUsername(),
                    Secrets.dbPassword()
            );

        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

}