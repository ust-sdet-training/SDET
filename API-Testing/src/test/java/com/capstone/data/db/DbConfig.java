package com.capstone.data.db;

import com.capstone.config.AppConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {
    private DbConfig() {
    }

    public static Connection connection() throws SQLException {
        return DriverManager.getConnection(
                AppConfig.get("db.url"),
                AppConfig.get("db.user"),
                AppConfig.get("db.password")
        );
    }
}
