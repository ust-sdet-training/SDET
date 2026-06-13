package ust.sdet.db.config;

import ust.sdet.db.model.OrderRow;

import java.sql.*;

public class DbSupport {
    private static DatabaseConfig config;

    public DbSupport(DatabaseConfig config) {
        this.config = config;
    }

    public boolean isReachable() throws SQLException {
        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT 1");
             ResultSet result = statement.executeQuery()) {
            return result.next() && result.getInt(1) == 1;
        }
    }

    public static OrderRow findOrder(String orderId) throws SQLException {
        String sql = """
        SELECT id, order_number, status, total, user_id, created_at
        FROM orders
        WHERE id = ?
        """;

        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, orderId);

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

    public int deleteOrder(String orderId) throws SQLException {
        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE id = ?")) {
            statement.setString(1, orderId);
            return statement.executeUpdate();
        }
    }

    private static Connection openConnection() throws SQLException {
        return DriverManager.getConnection(config.jdbcUrl(), config.username(), config.password());
    }
}
