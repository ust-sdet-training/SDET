package com.week7.finalgate.tests;

import com.week7.finalgate.API.Factory.RestClient;
import com.week7.finalgate.API.models.BookingResponse;
import com.week7.finalgate.API.service.AuthService;
import com.week7.finalgate.API.service.BookingService;
import com.week7.finalgate.API.service.FlightService;
import com.week7.finalgate.API.service.SeatService;
import com.week7.finalgate.API.support.ApiContext;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BookingFlowTest {

    @BeforeAll
    static void setup() {
        RestClient.initialize();
    }

    @Test
    void shouldCompleteBookingFlow() {

        ApiContext context =
                new ApiContext();

        new AuthService(context).login();

        new FlightService(context)
                .searchFlight(
                        "MAA",
                        "BLR",
                        LocalDate.now()
                                .plusDays(6)
                                .toString()
                );

        new SeatService(context)
                .selectFirstAvailableSeat();

        BookingService booking =
                new BookingService(context);

        BookingResponse held =
                booking.holdSeat();

        assertEquals(
                "HELD",
                held.getState()
        );

        BookingResponse paid =
                booking.pay();

        assertEquals(
                "PAYMENT_PENDING",
                paid.getState()
        );

        BookingResponse confirmed =
                booking.confirm();

        assertEquals(
                "CONFIRMED",
                confirmed.getState()
        );

        assertNotNull(
                confirmed.getPnr()
        );

        assertTrue(
                confirmed.getPnr()
                        .startsWith("TS-1021-")
        );

        System.out.println(
                "PNR : "
                        + confirmed.getPnr()
        );
    }
}