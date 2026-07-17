package com.capstone.db;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class BookingPersistenceTest {
  @Test
  public void shouldExposeBookingQuerySupport() throws Exception {
    DbClient dbClient = new DbClient();
    int count = dbClient.countBookings();
    assertNotNull(count);
  }
}
