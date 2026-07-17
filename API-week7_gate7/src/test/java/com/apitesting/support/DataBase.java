package com.apitesting.support;

import org.testcontainers.containers.MySQLContainer;

public class DataBase {

    public static MySQLContainer<?> mysql =
            new MySQLContainer<>("mysql:8.0")
                    .withDatabaseName("tripstack")
                    .withUsername("root123")
                    .withPassword("Formysql@123");

    static {
        mysql.start();
    }
}