package com.travelbooking.database;

import com.travelbooking.database.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {

    public static ResultSet executeQuery(String query, Object... params) {

        try {
            Connection connection = DatabaseConfig.getConnection();

            PreparedStatement statement = connection.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            return statement.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException("Database Query Failed : " + e.getMessage(), e);
        }
    }

    public static int executeUpdate(String query, Object... params) {

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            return statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Database Update Failed : " + e.getMessage(), e);
        }
    }
}