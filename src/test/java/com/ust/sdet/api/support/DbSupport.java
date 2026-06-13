package com.ust.sdet.api.support;

import com.ust.sdet.api.config.DatabaseConfig;
import com.ust.sdet.api.model.OrderRow;

import java.sql.*;

public final class DbSupport {

    private static DatabaseConfig config ;

    public DbSupport(DatabaseConfig config) {
        this.config = config;
    }

    public boolean isReachable() throws SQLException {
        try (Connection connection = openConnection()) {
            return connection.isValid(2);
        }
    }

    public static int deleteOrder(long orderId) throws SQLException {
        try (Connection connection = openConnection();
             var statement =
                     connection.prepareStatement("DELETE FROM orders WHERE id = ?")) {

            statement.setLong(1, orderId);
            return statement.executeUpdate();
        }
    }

    private static Connection openConnection() throws SQLException {
        return DriverManager.getConnection(config.jdbcUrl(), config.username(), config.password());
    }

    public static OrderRow findOrder(long orderId) throws SQLException {
        String sql = """
       SELECT id, order_number, status, total, user_id, created_at
       FROM orders
       WHERE id = ?
       """;
        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, orderId);
            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()) {
                    return null;
                }
                return new OrderRow(
                        result.getLong("id"),
                        result.getString("order_number"),
                        result.getString("status"),
                        result.getBigDecimal("total"),
                        result.getString("user_id"),
                        result.getTimestamp("created_at").toInstant()
                );
            }
        }
    }
}