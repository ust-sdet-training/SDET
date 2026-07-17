package com.ust.capstone.data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartRepository extends DatabaseSupport {

    public int cartTotal(long cartId) {

        try (
                Connection connection = connection();
                PreparedStatement statement =
                        connection.prepareStatement(
                                Queries.FIND_CART_TOTAL
                        )
        ) {

            statement.setLong(1, cartId);

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