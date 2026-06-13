package com.ust.sdet.api.tests.test_containers;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class TestContainersDemo {

    @Container
    static MySQLContainer<?> mysql =
            new MySQLContainer<>("mysql:8.4")
                    .withDatabaseName("retail")
                    .withUsername("test")
                    .withPassword("test");

    @Test
    void containerShouldStart() {

        System.out.println(
                "JDBC URL = "
                        + mysql.getJdbcUrl()
        );

        System.out.println(
                "Username = "
                        + mysql.getUsername()
        );
    }
}