package com.week7.finalgate.DB.assertions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class SeatAssertions {

    private SeatAssertions() {
    }

    public static void verifySeats(
            List<String> apiSeats,
            List<String> dbSeats
    ) {

        assertIterableEquals(
                apiSeats,
                dbSeats,
                "Seat mismatch"
        );

    }

}