package db;

import models.OrderRow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbSupport {

    private final String dbUrl;
    private final String username;
    private final String password;

    public DbSupport(String dbUrl,
                     String username,
                     String password) {

        this.dbUrl = dbUrl;
        this.username = username;
        this.password = password;
    }

    private Connection openConnection()
            throws SQLException {

        return DriverManager.getConnection(
                dbUrl,
                username,
                password);
    }

    public boolean isReachable()
            throws SQLException {

        try (Connection connection = openConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             "SELECT 1");
             ResultSet result =
                     statement.executeQuery()) {

            return result.next()
                    && result.getInt(1) == 1;
        }
    }

    public OrderRow findOrder(long orderId)
            throws SQLException {

        String query = """
                SELECT id,
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
                     connection.prepareStatement(query)) {

            statement.setLong(
                    1,
                    orderId);

            try (ResultSet result =
                         statement.executeQuery()) {

                if (!result.next()) {
                    return null;
                }

                return new OrderRow(
                        result.getLong("id"),
                        result.getString("order_number"),
                        result.getString("status"),
                        result.getBigDecimal("total"),
                        result.getString("user_id"),
                        result.getTimestamp("created_at")
                                .toInstant()
                );
            }
        }
    }

    public boolean orderExists(long orderId)
            throws SQLException {

        return findOrder(orderId) != null;
    }

    public boolean verifyOrderPersisted(long orderId)
            throws SQLException {

        return orderExists(orderId);
    }

    public String getOrderStatus(long orderId)
            throws SQLException {

        OrderRow order =
                findOrder(orderId);

        return order == null
                ? null
                : order.getStatus();
    }

    public boolean reconcileOrder(
            long orderId,
            String apiOrderNumber,
            String apiStatus)
            throws SQLException {

        OrderRow dbOrder =
                findOrder(orderId);

        if (dbOrder == null) {
            return false;
        }

        return apiOrderNumber.equals(
                dbOrder.getOrderNumber())
                &&
                apiStatus.equals(
                        dbOrder.getStatus());
    }

    public int deleteOrder(long orderId)
            throws SQLException {

        String query =
                "DELETE FROM orders WHERE id = ?";

        try (Connection connection = openConnection();
             PreparedStatement statement =
                     connection.prepareStatement(query)) {

            statement.setLong(
                    1,
                    orderId);

            return statement.executeUpdate();
        }
    }

    public boolean cleanupOrder(long orderId)
            throws SQLException {

        return deleteOrder(orderId) > 0;
    }
}