package com.ust.capstone.data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRepository extends DatabaseSupport {

    public boolean orderExists(long orderId) {

        try (
                Connection connection = connection();
                PreparedStatement statement =
                        connection.prepareStatement(Queries.FIND_ORDER_BY_ID)
        ) {

            statement.setLong(1, orderId);

            ResultSet result = statement.executeQuery();

            return result.next();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    public String orderStatus(long orderId) {

        try (
                Connection connection = connection();
                PreparedStatement statement =
                        connection.prepareStatement(Queries.FIND_ORDER_STATUS)
        ) {

            statement.setLong(1, orderId);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getString("status");
            }

            return null;

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    public int placedOrdersForCustomer(long customerId) {

        try (
                Connection connection = connection();
                PreparedStatement statement =
                        connection.prepareStatement(
                                Queries.COUNT_PLACED_ORDERS_FOR_CUSTOMER
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