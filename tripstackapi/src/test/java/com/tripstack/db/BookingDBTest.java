package com.tripstack.db;

import com.tripstack.api.BookingTest;
import com.tripstack.database.DatabaseValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookingDBTest {

    @Test
    void verifyBookingExistsInDatabase() throws Exception {

        String bookingId = "BK-1017-0001";

        assertNotNull(bookingId);

        assertTrue(
                DatabaseValidator.bookingExists(bookingId)
        );

        assertNotNull(
                DatabaseValidator.getBookingPNR(bookingId)
        );

        assertNotNull(
                DatabaseValidator.getBookingStatus(bookingId)
        );
    }
}