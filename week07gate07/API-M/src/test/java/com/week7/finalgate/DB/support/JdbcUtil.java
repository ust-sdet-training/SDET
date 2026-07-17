package com.week7.finalgate.DB.support;

import com.week7.finalgate.DB.config.DbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class JdbcUtil {

    private JdbcUtil() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DbConfig.URL,
                DbConfig.USER,
                DbConfig.PASSWORD
        );
    }
}