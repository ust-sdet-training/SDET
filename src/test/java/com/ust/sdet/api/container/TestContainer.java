package com.ust.sdet.api.container;

import com.ust.sdet.api.support.EnvCheck;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
    public class TestContainer {

    @Container
    static MySQLContainer<?> mysql =
            new MySQLContainer<>("mysql:8.4")
                    .withDatabaseName(EnvCheck.required("DB_NAME"))
                    .withUsername(EnvCheck.required("DB_USER"))
                    .withPassword(EnvCheck.required("DB_PASSWORD"));

    @Test
    void containerShouldStart() {

        System.out.println("JDBC URL = " + mysql.getJdbcUrl());
        System.out.println("Username = " + mysql.getUsername());
        System.out.println("Database = " + mysql.getDatabaseName());
    }

}


