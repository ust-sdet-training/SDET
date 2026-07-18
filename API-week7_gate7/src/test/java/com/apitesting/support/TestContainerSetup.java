package com.apitesting.support;

import org.testcontainers.containers.MySQLContainer;

public class TestContainerSetup {

    public static final MySQLContainer<?> mysql =
            new MySQLContainer<>("mysql:8.0")
                    .withDatabaseName("tripstack_db")
                    .withUsername("root")
                    .withPassword("root");

    static {
        mysql.start();
    }
}