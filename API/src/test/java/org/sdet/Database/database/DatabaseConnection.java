package org.sdet.Database.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection;

    private DatabaseConnection() {
    }

    public static Connection getConnection() {

        try {

            if (connection == null || connection.isClosed()) {

                connection = DriverManager.getConnection(
                        DBConfig.URL,
                        DBConfig.USERNAME,
                        DBConfig.PASSWORD
                );
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Unable to connect to database", e);
        }

        return connection;
    }

    public static void closeConnection() {

        try {

            if (connection != null &&
                    !connection.isClosed()) {

                connection.close();
            }

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }
}