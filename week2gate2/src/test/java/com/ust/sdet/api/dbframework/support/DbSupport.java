package com.ust.sdet.api.dbframework.support;

import com.ust.sdet.api.dbframework.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DbSupport {

    private final DatabaseConfig config;

    public DbSupport(DatabaseConfig config) {
        this.config = config;
    }

    public Connection openConnection() throws SQLException {
        return DriverManager.getConnection(
                config.jdbcUrl(),
                config.username(),
                config.password()
        );
    }

    public boolean isReachable() throws SQLException {

        try (Connection connection = openConnection()) {
            return connection.isValid(5);
        }
    }
}