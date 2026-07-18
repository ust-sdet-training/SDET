package com.travelbooking.database.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    public static Connection getConnection() throws SQLException {

        String url = System.getenv("DB_URL");
        if (url == null || url.isBlank()) {
            url = dotenv.get("DB_URL");
        }

        String username = System.getenv("DB_USERNAME");
        if (username == null || username.isBlank()) {
            username = dotenv.get("DB_USERNAME");
        }

        String password = System.getenv("DB_PASSWORD");
        if (password == null || password.isBlank()) {
            password = dotenv.get("DB_PASSWORD");
        }

        return DriverManager.getConnection(
                url,
                username,
                password
        );
    }
}