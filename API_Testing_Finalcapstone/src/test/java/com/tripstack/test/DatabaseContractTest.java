package com.tripstack.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.Instant;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import com.tripstack.database.BookingRepository;
import com.tripstack.database.DatabaseManager;
import com.tripstack.model.BookingResponse;

class DatabaseContractTest {
    private static PostgreSQLContainer<?> postgres;

    @BeforeAll
    static void configureDatabase() throws Exception {
        if (isCi()) {
            postgres = new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("tripstack")
                    .withUsername("tripstack")
                    .withPassword("tripstack");
            postgres.start();

            System.setProperty("DB_JDBC_URL", postgres.getJdbcUrl());
            System.setProperty("DB_USER", postgres.getUsername());
            System.setProperty("DB_PASSWORD", postgres.getPassword());
        } else {
            System.setProperty("DB_JDBC_URL", "jdbc:h2:mem:tripstack;MODE=PostgreSQL;DB_CLOSE_DELAY=-1");
            System.setProperty("DB_USER", "sa");
            System.setProperty("DB_PASSWORD", "");
        }

        createBookingsTable();
    }

    @AfterAll
    static void cleanUp() {
        System.clearProperty("DB_JDBC_URL");
        System.clearProperty("DB_USER");
        System.clearProperty("DB_PASSWORD");
        if (postgres != null) {
            postgres.stop();
        }
    }

    @Test
    void bookingRepositoryReadsTheSharedBookingsSchema() throws Exception {
        String bookingId = "db-contract-001";
        String pnr = "TS-TEST-0001";
        insertBooking(bookingId, pnr, "CONFIRMED");

        BookingResponse booking = new BookingRepository().findBookingById(bookingId);

        assertNotNull(booking, "Repository should read the inserted booking");
        assertEquals(bookingId, booking.getId(), "Booking ID should be preserved");
        assertEquals(pnr, booking.getPnr(), "PNR should be preserved");
        assertEquals("CONFIRMED", booking.getState(), "Booking state should be preserved");
        assertEquals("FL-MAAHYD-51", booking.getInventoryId(), "Inventory ID should be preserved");
    }

    private static boolean isCi() {
        return "true".equalsIgnoreCase(System.getenv("CI"));
    }

    private static void createBookingsTable() throws Exception {
        String sql = """
                CREATE TABLE IF NOT EXISTS bookings (
                    booking_id VARCHAR(64) PRIMARY KEY,
                    pnr VARCHAR(64),
                    emp_id VARCHAR(64) NOT NULL,
                    journey_type VARCHAR(32) NOT NULL,
                    inventory_id VARCHAR(64) NOT NULL,
                    state VARCHAR(32) NOT NULL,
                    amount_paise BIGINT NOT NULL,
                    refundable BOOLEAN NOT NULL,
                    hold_expires_at TIMESTAMP
                )
                """;
        try (Connection connection = DatabaseManager.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    private static void insertBooking(String bookingId, String pnr, String state) throws Exception {
        String sql = """
                INSERT INTO bookings (booking_id, pnr, emp_id, journey_type, inventory_id, state, amount_paise, refundable, hold_expires_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = DatabaseManager.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bookingId);
            statement.setString(2, pnr);
            statement.setString(3, "test-employee");
            statement.setString(4, "flight");
            statement.setString(5, "FL-MAAHYD-51");
            statement.setString(6, state);
            statement.setLong(7, 123_450L);
            statement.setBoolean(8, true);
            statement.setTimestamp(9, java.sql.Timestamp.from(Instant.parse("2030-01-01T00:00:00Z")));
            statement.executeUpdate();
        }
    }
}
