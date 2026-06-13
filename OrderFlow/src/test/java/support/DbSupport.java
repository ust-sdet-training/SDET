package support;

import config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbSupport {

    // Get order status from DB
    public static String getOrderStatus(int orderId) throws Exception {

        Connection conn = DatabaseConfig.getConnection();

        PreparedStatement ps = conn.prepareStatement(
                "SELECT status FROM orders WHERE id = ?"
        );

        ps.setInt(1, orderId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getString("status");
        }

        conn.close();
        return null;
    }
}