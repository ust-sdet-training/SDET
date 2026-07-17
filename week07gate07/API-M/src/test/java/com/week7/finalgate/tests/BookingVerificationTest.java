package com.week7.finalgate.tests;

import com.week7.finalgate.API.Factory.RestClient;
import com.week7.finalgate.API.models.BookingResponse;
import com.week7.finalgate.API.service.*;
import com.week7.finalgate.API.support.ApiContext;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingVerificationTest {

    @BeforeAll
    static void setup() {
        RestClient.initialize();
    }

    @Test
    void verifyBookingExists() {

        ApiContext context = new ApiContext();

        new AuthService(context).login();

        new FlightService(context)
                .searchFlight(
                        "MAA",
                        "BLR",
                        LocalDate.now().plusDays(6).toString()
                );

        new SeatService(context)
                .selectFirstAvailableSeat();

        BookingService bookingService =
                new BookingService(context);

        bookingService.holdSeat();

        bookingService.pay();

        BookingResponse confirmed =
                bookingService.confirm();

        BookingListService listService =
                new BookingListService(context);

        List<BookingResponse> bookings =
                listService.getMyBookings();

        assertFalse(bookings.isEmpty());

        boolean found =
                bookings.stream()
                        .anyMatch(
                                b -> confirmed.getPnr()
                                        .equals(b.getPnr())
                        );

        assertTrue(found);

        BookingDetailsService details =
                new BookingDetailsService(context);

        BookingResponse booking =
                details.getBookingByPNR();

        assertEquals(
                confirmed.getPnr(),
                booking.getPnr()
        );

        assertEquals(
                "CONFIRMED",
                booking.getState()
        );

        assertEquals(
                "1021",
                booking.getEmpId()
        );
    }

}
