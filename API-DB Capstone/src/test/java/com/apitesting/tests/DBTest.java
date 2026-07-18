package com.apitesting.tests;

import com.apitesting.repository.BookingRepository;
import com.apitesting.support.db.DbConnectionProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DBTest {

    @Test
    void shouldSaveBooking() {

        BookingRepository repository = DbConnectionProvider.getRepository();

        repository.reset();

        BookingRepository.Booking booking =
                new BookingRepository.Booking("TS-1023-0001", "FL-PUNDEL-51", "ONE_WAY", "CONFIRMED", "1023", true, "2A");

        repository.save(booking);

        BookingRepository.Booking saved = repository.findByPnr("TS-1023-0001");

        assertNotNull(saved);
        assertEquals("1023", saved.empId());
        assertEquals("CONFIRMED", saved.status());
        assertEquals("2A", saved.seatId());
    }
}