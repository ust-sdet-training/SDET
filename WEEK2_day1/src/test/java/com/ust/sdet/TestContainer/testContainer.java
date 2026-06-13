package com.ust.sdet.TestContainer;

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
                    .withUsername("test1")
                    .withPassword("test1");

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