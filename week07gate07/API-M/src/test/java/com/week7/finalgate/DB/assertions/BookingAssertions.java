package com.week7.finalgate.DB.assertions;

import com.week7.finalgate.API.models.BookingResponse;
import com.week7.finalgate.DB.model.BookingRecord;

import static org.junit.jupiter.api.Assertions.*;

public class BookingAssertions {

    private BookingAssertions() {
    }

    public static void verifyBooking(
            BookingResponse api,
            BookingRecord db
    ) {

        assertAll(

                "Booking Validation",

                () -> assertEquals(
                        api.getId(),
                        db.getBookingUuid(),
                        "Booking UUID mismatch"
                ),

                () -> assertEquals(
                        api.getPnr(),
                        db.getPnr(),
                        "PNR mismatch"
                ),

                () -> assertEquals(
                        api.getEmpId(),
                        db.getEmpId(),
                        "Employee ID mismatch"
                ),

                () -> assertEquals(
                        api.getJourneyType(),
                        db.getJourneyType(),
                        "Journey Type mismatch"
                ),

                () -> assertEquals(
                        api.getInventoryId(),
                        db.getInventoryId(),
                        "Inventory mismatch"
                ),

                () -> assertEquals(
                        api.getState(),
                        db.getBookingState(),
                        "Booking State mismatch"
                ),

                () -> assertEquals(
                        api.getAmountPaise(),
                        db.getAmountPaise(),
                        "Amount mismatch"
                ),

                () -> assertEquals(
                        api.isRefundable(),
                        db.isRefundable(),
                        "Refundable flag mismatch"
                )

        );

    }

}
