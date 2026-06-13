package database;

import config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseUtils {

    public static String getOrderStatus(int orderId) {

        String query = "SELECT status FROM orders WHERE id=?";
        try (
                Connection connection = DatabaseConfig.getConnection();

                PreparedStatement statement = connection.prepareStatement(query)
        ) {

            statement.setInt(1, orderId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getString("status");
            }

        } catch (Exception e) {

            throw new RuntimeException("Database validation failed", e);
        }

        return null;
    }
}