package com.travelbooking.tests.database;

import com.travelbooking.database.models.Passenger;
import com.travelbooking.database.queries.PassengerQueries;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PassengerDatabaseTest {

    @Test
    void shouldValidatePassengerDetails() {

        PassengerQueries passengerQueries = new PassengerQueries();

        Passenger passenger = passengerQueries.getPassengerByBookingId(1);

        assertNotNull(passenger);
        assertEquals("Sai", passenger.getFirstName());
        assertEquals("Teja", passenger.getLastName());
        assertEquals(24, passenger.getAge());
        assertEquals("Male", passenger.getGender());
    }
}