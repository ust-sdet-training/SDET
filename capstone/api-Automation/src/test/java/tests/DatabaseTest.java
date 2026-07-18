package tests;


import database.BookingDBValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    @Test
    void shouldInsertBookingIntoDatabase(){

        BookingDBValidator validator =
                new BookingDBValidator();

        validator.insertBooking(
                "B001",
                "flight",
                "FL-DELBLR-51",
                "HELD"
        );

        assertTrue(
                validator.bookingExists("B001")
        );

        assertEquals(
                "HELD",
                validator.getState("B001")
        );
    }
}