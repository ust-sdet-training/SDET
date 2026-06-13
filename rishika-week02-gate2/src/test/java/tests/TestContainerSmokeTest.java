package tests;

import org.junit.jupiter.api.Test;

import org.testcontainers.containers.MySQLContainer;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestContainerSmokeTest {

    @Test

    void mysqlContainerShouldStart() {

        try (MySQLContainer<?> mysql =

                     new MySQLContainer<>("mysql:8.0")) {

            mysql.start();

            assertTrue(mysql.isRunning());

            System.out.println("JDBC URL: " + mysql.getJdbcUrl());

            System.out.println("Username: " + mysql.getUsername());

            System.out.println("Password: " + mysql.getPassword());

        }

    }

}
