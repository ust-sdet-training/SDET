package com.ust.sdet.api.DbFramework.Repository;

import com.ust.sdet.api.DbFramework.Model.OrderRow;
import com.ust.sdet.api.DbFramework.Support.DbSupport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRepo {

    private final DbSupport dbSupport;

    public OrderRepo(DbSupport dbSupport) {
        this.dbSupport = dbSupport;
    }

    public OrderRow findOrder(long orderId) throws SQLException {

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

        String sql = """
                DELETE FROM orders
                WHERE id = ?
                """;

        try (Connection connection = dbSupport.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, orderId);

            return statement.executeUpdate();
        }
    }
}