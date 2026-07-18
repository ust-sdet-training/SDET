package com.capstone.tests;

import com.capstone.data.db.BookingRecord;
import com.capstone.data.db.BookingRepository;
import com.capstone.data.db.DbSeeder;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@DisabledIfSystemProperty(named = "use.testcontainers", matches = "true")
public class BookingDbTest {

    private final BookingRepository bookingRepository = new BookingRepository();

    @Test
    void localDatabaseTest() throws SQLException {
        DbSeeder.seedConfirmedBusBooking();

        BookingRecord databaseBooking = bookingRepository.findByPnr(DbSeeder.PNR)
                .orElseThrow(() -> new AssertionError("Seeded booking was not found in the database"));

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
