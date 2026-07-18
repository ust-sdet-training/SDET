package api.tripstack.tests;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
class DatabaseContainerTest {

    @Container
    private static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.4")
            .withDatabaseName("tripstack")
            .withUsername("tripstack")
            .withPassword("tripstack");

    @Test
    void createsAndQueriesBookingRowsInContainerizedMysql() throws Exception {
        try (Connection connection = DriverManager.getConnection(
                mysql.getJdbcUrl(),
                mysql.getUsername(),
                mysql.getPassword())) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS bookings (id INT PRIMARY KEY, emp_id VARCHAR(20), pnr VARCHAR(50))");
                statement.execute("DELETE FROM bookings");
                statement.execute("INSERT INTO bookings VALUES (1, '1030', 'TS-1030-0001')");
            }

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT emp_id, pnr FROM bookings WHERE id = 1")) {
                assertTrue(resultSet.next(), "Expected a booking row to be present");
                assertEquals("1030", resultSet.getString("emp_id"));
                assertEquals("TS-1030-0001", resultSet.getString("pnr"));
            }
        }
    }
}
