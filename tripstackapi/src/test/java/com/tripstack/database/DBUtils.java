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

    public static Connection getConnection() throws SQLException {

        TestContainerManager.startContainer();

        return DriverManager.getConnection(
                TestContainerManager.getJdbcUrl(),
                TestContainerManager.getUsername(),
                TestContainerManager.getPassword()
        );
    }

    public static ResultSet executeQuery(Connection connection,
                                         String query,
                                         Object... parameters)
            throws SQLException {

        PreparedStatement preparedStatement =
                connection.prepareStatement(query);

        for (int i = 0; i < parameters.length; i++) {
            preparedStatement.setObject(i + 1, parameters[i]);
        }

        return preparedStatement.executeQuery();
    }

    public static int executeUpdate(Connection connection,
                                    String query,
                                    Object... parameters)
            throws SQLException {

        PreparedStatement preparedStatement =
                connection.prepareStatement(query);

        for (int i = 0; i < parameters.length; i++) {
            preparedStatement.setObject(i + 1, parameters[i]);
        }

        return preparedStatement.executeUpdate();
    }

    public static Object getSingleValue(String query,
                                        Object... parameters)
            throws SQLException {

        Connection connection = getConnection();

        PreparedStatement preparedStatement =
                connection.prepareStatement(query);

        for (int i = 0; i < parameters.length; i++) {
            preparedStatement.setObject(i + 1, parameters[i]);
        }

        ResultSet resultSet = preparedStatement.executeQuery();

        Object value = null;

        if (resultSet.next()) {
            value = resultSet.getObject(1);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return value;
    }

    public static void close(Connection connection) {

        try {

            if (connection != null && !connection.isClosed()) {
                connection.close();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}