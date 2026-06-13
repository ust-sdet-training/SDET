package week2.gate2.support;


import week2.gate2.config.DatabaseConfig;
import week2.gate2.model.OrderRow;

import java.sql.*;

public final class DbSupport {

    private final DatabaseConfig config;
    public DbSupport(DatabaseConfig config) {
        this.config = config;
    }

    public Connection openConnection() throws SQLException {
        return DriverManager.getConnection(config.jdbcUrl(), config.username(), config.password());
    }

    public boolean isReachable() throws SQLException {
        try (Connection connection = openConnection()) {
            return connection.isValid(5);
        }
    }
}

