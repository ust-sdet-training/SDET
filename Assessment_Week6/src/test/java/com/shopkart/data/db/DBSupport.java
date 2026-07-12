package com.shopkart.data.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBSupport {

    private static final String URL =
            "jdbc:mysql://localhost:3306/shopkart";

    private static final String USER = "shopkart_user";

    private static final String PASSWORD = "YourPassword@123";

    public static Connection getConnection() throws SQLException, SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}