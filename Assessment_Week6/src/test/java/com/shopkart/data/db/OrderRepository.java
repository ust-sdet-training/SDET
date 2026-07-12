package com.shopkart.data.db;

import java.sql.*;

public class OrderRepository {

    private final Connection connection;

    public OrderRepository(Connection connection) {
        this.connection = connection;
    }

    public String getOrderStatus(int orderId) throws SQLException {

        String sql =
                "SELECT status FROM orders WHERE id=?";

        PreparedStatement ps =
                connection.prepareStatement(sql);

        ps.setInt(1, orderId);

        ResultSet rs = ps.executeQuery();

        rs.next();

        return rs.getString("status");
    }

    public int getTotalPaise(int orderId) throws SQLException {

        String sql =
                "SELECT total_paise FROM orders WHERE id=?";

        PreparedStatement ps =
                connection.prepareStatement(sql);

        ps.setInt(1, orderId);

        ResultSet rs = ps.executeQuery();

        rs.next();

        return rs.getInt("total_paise");
    }
}