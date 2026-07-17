package com.capstone.data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Repository extends DbSupport {
    public String status(long orderId) {
        try (
                Connection connection = connection();
                PreparedStatement statement = connection.prepareStatement(SqlQueries.ORDER_STATUS)
        ) {

            statement.setLong(1, orderId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString("status");
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int totalPaise(long orderId) {
        try (
                Connection connection = connection();
                PreparedStatement statement =
                        connection.prepareStatement(SqlQueries.ORDER_TOTAL)
        ) {
            statement.setLong(1, orderId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_paise");
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long customerId(String persona) {
        try (
                Connection connection = connection();
                PreparedStatement statement =
                        connection.prepareStatement(SqlQueries.CUSTOMER_ID)
        ) {
            statement.setString(1, persona);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getLong("id");
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int placedOrders(long customerId) {
        try (
                Connection connection = connection();
                PreparedStatement statement =
                        connection.prepareStatement(
                                SqlQueries.PLACED_ORDER_COUNT
                        )
        ) {
            statement.setLong(1, customerId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
