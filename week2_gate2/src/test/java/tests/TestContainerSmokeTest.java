package tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestContainerSmokeTest {

    @Test
    <MySQLContainer>
    void mysqlContainerShouldStart() {

        try (MySQLContainer<?> mysql =
                     new MySQLContainer<>("mysql:8.0")) {

            mysql.start();

            assertTrue(mysql.toString());

            System.out.println("JDBC URL: " + mysql.getJdbcUrl());
            System.out.println("Username: " + mysql.getUsername());
            System.out.println("Password: " + mysql.getPassword());
        }
    }
}
