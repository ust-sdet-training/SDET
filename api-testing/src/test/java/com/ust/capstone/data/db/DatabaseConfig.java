package com.ust.capstone.data.db;

import com.ust.capstone.data.secret.Secrets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConfig {

    private DatabaseConfig() {
    }

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                Secrets.get("DB_JDBC_URL"),
                Secrets.get("DB_USER"),
                Secrets.get("DB_PASSWORD")
        );
    }
}