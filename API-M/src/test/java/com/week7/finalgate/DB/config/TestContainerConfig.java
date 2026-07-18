package com.week7.finalgate.DB.config;

import com.week7.finalgate.DB.support.DatabaseInitializer;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.MySQLContainer;

public class TestContainerConfig {

    protected static final MySQLContainer<?> MYSQL =
            new MySQLContainer<>("mysql:8.4")
                    .withDatabaseName("tripstack")
                    .withUsername("root")
                    .withPassword("root");

    @BeforeAll
    public static void startContainer() {

        if (!MYSQL.isRunning()) {

            MYSQL.start();

            System.setProperty(
                    "DB_URL",
                    MYSQL.getJdbcUrl()
            );

            System.setProperty(
                    "DB_USERNAME",
                    MYSQL.getUsername()
            );

            System.setProperty(
                    "DB_PASSWORD",
                    MYSQL.getPassword()
            );

            DatabaseInitializer.initialize();

        }

    }

}