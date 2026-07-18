package com.travelbooking.config;

import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.MySQLContainer;

public class TestContainerConfig {

    protected static final MySQLContainer<?> mysql =
            new MySQLContainer<>("mysql:8.4")
                    .withDatabaseName("travel_booking")
                    .withUsername("root")
                    .withPassword("root");

    @BeforeAll
    static void startContainer() {

        if (!mysql.isRunning()) {
            mysql.start();
        }

        System.setProperty("DB_URL", mysql.getJdbcUrl());
        System.setProperty("DB_USERNAME", mysql.getUsername());
        System.setProperty("DB_PASSWORD", mysql.getPassword());
    }
}