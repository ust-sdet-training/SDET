package dbframework.support;

import dbframework.config.DBConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {

    private DBConnection() {
    }

    public static Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                    DBConfig.URL,
                    DBConfig.USERNAME,
                    DBConfig.PASSWORD
            );

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Unable to connect to MySQL.", e);
        }
    }
}