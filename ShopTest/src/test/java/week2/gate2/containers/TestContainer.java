package week2.gate2.containers;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class TestContainer {
    @Container
    static MySQLContainer<?> mysql =
            new MySQLContainer<>("mysql:8.4")
                    .withDatabaseName("retail")
                    .withUsername("root")
                    .withPassword("password");

    @Test
    void containerShouldStart() {
        System.out.println("JDBC URL = " + mysql.getJdbcUrl());

        System.out.println("Username = " + mysql.getUsername());
    }
}
