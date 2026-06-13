package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class TestContainer {

    @Test
    @DisplayName("Verify PostgreSQL TestContainer with JDBC")
    void verifyPostgresContainer() throws Exception {

        try (PostgreSQLContainer<?> postgres =
                     new PostgreSQLContainer<>("postgres:16")) {

            postgres.start();

            assertTrue(
                    postgres.isRunning(),
                    "Container should be running"
            );

            try (Connection connection =
                         DriverManager.getConnection(
                                 postgres.getJdbcUrl(),
                                 postgres.getUsername(),
                                 postgres.getPassword());

                 Statement statement =
                         connection.createStatement()) {

                statement.execute(
                        """
                        CREATE TABLE products(
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(100)
                        )
                        """
                );

                statement.execute(
                        """
                        INSERT INTO products(name)
                        VALUES ('Laptop')
                        """
                );

                ResultSet result =
                        statement.executeQuery(
                                "SELECT COUNT(*) FROM products"
                        );

                assertTrue(result.next());

                assertEquals(
                        1,
                        result.getInt(1)
                );
            }
        }
    }
}