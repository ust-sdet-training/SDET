package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DButils {

    private DButils() {
    }

    public static Connection getConnection() throws Exception {

        return DriverManager.getConnection(
                ConfigReader.getDbUrl(),
                ConfigReader.getDbUsername(),
                ConfigReader.getDbPassword());
    }

    public static ResultSet executeQuery(String query)
            throws Exception {

        Connection connection =
                getConnection();

        Statement statement =
                connection.createStatement();

        return statement.executeQuery(query);
    }

    public static ResultSet getOrderById(long orderId)
            throws Exception {

        String query =
                "SELECT * FROM orders WHERE id = " + orderId;

        return executeQuery(query);
    }
}