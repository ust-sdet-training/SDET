package com.tripstack.db;

import com.tripstack.database.DatabaseValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingNamespaceTest {

    @Test
    void verifyBookingNamespace() throws Exception {

        String bookingId = "BK-1017-0001";

        assertEquals(
                "1017",
                DatabaseValidator.getEmployeeId(bookingId)
        );

        String pnr = DatabaseValidator.getBookingPNR(bookingId);

        assertTrue(pnr.startsWith("TS-1017-"));
    }
}