package com.apitesting.support.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                DataBase.mysql.getJdbcUrl(),
                DataBase.mysql.getUsername(),
                DataBase.mysql.getPassword()
        );
    }
}
