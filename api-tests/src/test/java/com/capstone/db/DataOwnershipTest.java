package com.capstone.db;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("db")
public class DataOwnershipTest {
    @Test
    public void shouldReadBookingCount() throws Exception {
        DbClient dbClient = new DbClient();
        int count = dbClient.countBookings();
        assertTrue(count >= 0);
    }
}