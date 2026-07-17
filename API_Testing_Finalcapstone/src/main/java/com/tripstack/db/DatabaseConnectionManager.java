package com.tripstack.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.tripstack.config.TestConfig;

public class DatabaseConnectionManager {
    public Connection getConnection() throws SQLException {
        String url = TestConfig.getDbUrl();
        String user = TestConfig.getDbUser();
        String password = TestConfig.getDbPassword();
        return DriverManager.getConnection(url, user, password);
    }
}
