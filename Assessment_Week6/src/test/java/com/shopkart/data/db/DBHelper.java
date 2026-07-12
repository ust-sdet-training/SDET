package com.shopkart.data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBHelper {

    public static int getCartTotal(int cartId) {
        String sql =
                "SELECT SUM(qty * unit_price_paise) " +
                        "FROM cart_items " +
                        "WHERE cart_id = ?";

        try (
                Connection conn = DBSupport.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, cartId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}