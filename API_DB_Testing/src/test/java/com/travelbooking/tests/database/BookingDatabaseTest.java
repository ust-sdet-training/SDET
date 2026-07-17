package com.travelbooking.database;

import com.travelbooking.database.models.Booking;
import com.travelbooking.database.queries.BookingQueries;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookingDatabaseTest {

    @Test
    void shouldValidateBookingDetails() {

        BookingQueries bookingQueries = new BookingQueries();

        Booking booking = bookingQueries.getBookingById(1);

        assertNotNull(booking);
        assertEquals("TS-1026-0001", booking.getPnr());
        assertEquals("LKO", booking.getSourceCity());
        assertEquals("BLR", booking.getDestinationCity());
        assertEquals("CONFIRMED", booking.getBookingStatus());
    }
}