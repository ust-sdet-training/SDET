package support;



import config.DatabaseConfig;
import models.OrderRow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DbSupport {

    private final DatabaseConfig config;

    public DbSupport(DatabaseConfig config) {
        this.config = config;
    }

    public boolean isReachable() throws SQLException {

        try (Connection connection = openConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT 1");
             ResultSet result = statement.executeQuery()) {

            return result.next()
                    && result.getInt(1) == 1;
        }
    }

    public boolean orderExists(long orderId)
            throws SQLException {

        String sql =
                "SELECT COUNT(*) FROM orders WHERE id = ?";

        try (Connection connection = openConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, orderId);

            try (ResultSet result = statement.executeQuery()) {

                result.next();

                return result.getInt(1) > 0;
            }
        }
    }

    public OrderRow findOrder(long orderId)
            throws SQLException {

        String sql = """
                SELECT
                    id,
                    order_number,
                    status,
                    total,
                    user_id,
                    created_at
                FROM orders
                WHERE id = ?
                """;

        try (Connection connection = openConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

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

    public void createOrdersTableIfNotExists() throws SQLException {

        String sql = """
            CREATE TABLE IF NOT EXISTS orders (
                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                order_number VARCHAR(100),
                status VARCHAR(50),
                total DECIMAL(10,2),
                user_id VARCHAR(100),
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;

        try (Connection connection = openConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.execute();
        }
    }

    public String findOrderStatus(long orderId)
            throws SQLException {

        String sql =
                "SELECT status FROM orders WHERE id = ?";

        try (Connection connection = openConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, orderId);

            try (ResultSet result = statement.executeQuery()) {

                if (!result.next()) {
                    return null;
                }

                return result.getString("status");
            }
        }
    }

    public int deleteOrder(long orderId)
            throws SQLException {

        String sql =
                "DELETE FROM orders WHERE id = ?";

        try (Connection connection = openConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, orderId);

            return statement.executeUpdate();
        }
    }

    private Connection openConnection()
            throws SQLException {

        return DriverManager.getConnection(
                config.jdbcUrl(),
                config.username(),
                config.password()
        );
    }
}