package com.ust.sdet.tests;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
@Testcontainers
public class TestContainer {
    @Container
    static MySQLContainer<?> mysql =
            new MySQLContainer<>("mysql:8.0")
                    .withDatabaseName("retail")
                    .withUsername("test")
                    .withPassword("test")
                    .withInitScript("db/schema.sql");

    @Test
    void containerShouldStart() {
        System.out.println("JDBC URL: " + mysql.getJdbcUrl());
        System.out.println("Username : " + mysql.getUsername());
        System.out.println("Password : " + mysql.getPassword());

        assert mysql.isRunning();
    }
}
