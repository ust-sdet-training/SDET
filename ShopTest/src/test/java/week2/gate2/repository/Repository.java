package week2.gate2.repository;

import week2.gate2.model.OrderRow;
import week2.gate2.support.DbSupport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Repository {

    private final DbSupport dbSupport;

    public Repository(DbSupport dbSupport) {
        this.dbSupport = dbSupport;
    }

    public OrderRow findOrder(long orderId) throws SQLException {
        String sql = """
        SELECT id, order_number, status, total, user_id, created_at
        FROM orders
        WHERE id = ?
        """;
        try (Connection connection = dbSupport.openConnection();
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

    public int deleteOrder(long orderId) throws SQLException {
        try (Connection connection = dbSupport.openConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE id = ?")) {
            statement.setLong(1, orderId);
            return statement.executeUpdate();
        }
    }
}