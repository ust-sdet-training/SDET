package com.apitesting.support.db;

import com.apitesting.data.testUser;
import org.testcontainers.containers.MySQLContainer;

public class DataBase {

    public static final MySQLContainer<?> mysql =
            new MySQLContainer<>("mysql:8.0")
                    .withDatabaseName("tripstack")
                    .withUsername(testUser.DB_USER())
                    .withPassword(testUser.DB_PASSWORD());

    static {
        mysql.start();
    }
}