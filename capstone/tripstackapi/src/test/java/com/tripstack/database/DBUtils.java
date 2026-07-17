package com.tripstack.database;

import com.tripstack.base.TestContainerManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DBUtils {

    private DBUtils() {
    }

    public static Connection getConnection() {

        try {

            return DriverManager.getConnection(
                    TestContainerManager.getJdbcUrl(),
                    TestContainerManager.getUsername(),
                    TestContainerManager.getPassword()
            );

        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to database", e);
        }
    }

    public static int getCount(String sql, Object... params) {

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setParameters(statement, params);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }

                return 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getString(String sql, Object... params) {

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setParameters(statement, params);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getString(1);
                }

                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int executeUpdate(String sql, Object... params) {

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setParameters(statement, params);

            return statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setParameters(PreparedStatement statement, Object... params)
            throws SQLException {

        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
    }
}