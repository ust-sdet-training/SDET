package com.capstone.tests;

import com.capstone.data.db.BookingRecord;
import com.capstone.data.db.BookingRepository;
import com.capstone.data.db.DbSeeder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@EnabledIfSystemProperty(named = "use.testcontainers", matches = "true")
@Testcontainers(disabledWithoutDocker = true)
class BookingDbContainerTest {

    @Container
    private static final MySQLContainer<?> MYSQL = new MySQLContainer<>("mysql:8.4")
            .withDatabaseName("tripstack")
            .withUsername("tripstack_user")
            .withPassword("tripstack_password");

    private final BookingRepository bookingRepository = new BookingRepository();

    @BeforeAll
    static void configureDatabaseConnection() {
        System.setProperty("db.url", MYSQL.getJdbcUrl());
        System.setProperty("db.user", MYSQL.getUsername());
        System.setProperty("db.password", MYSQL.getPassword());
    }

    @Test
    void shouldCreateAndReadSeededConfirmedBusBookingInContainer() throws SQLException {
        DbSeeder.seedConfirmedBusBooking();

        BookingRecord databaseBooking = bookingRepository.findByPnr(DbSeeder.PNR)
                .orElseThrow(() -> new AssertionError("Seeded booking was not found in the Testcontainer database"));

        assertAll(
                () -> assertEquals(DbSeeder.BOOKING_ID, databaseBooking.id()),
                () -> assertEquals(DbSeeder.PNR, databaseBooking.pnr()),
                () -> assertEquals(DbSeeder.EMP_ID, databaseBooking.empId()),
                () -> assertEquals("bus", databaseBooking.journeyType()),
                () -> assertEquals(DbSeeder.INVENTORY_ID, databaseBooking.inventoryId()),
                () -> assertEquals("CONFIRMED", databaseBooking.state()),
                () -> assertEquals("[\"" + DbSeeder.SEAT_ID + "\"]", databaseBooking.seatIds()),
                () -> assertEquals(129885L, databaseBooking.amountPaise()),
                () -> assertTrue(databaseBooking.refundable())
        );
    }
}
