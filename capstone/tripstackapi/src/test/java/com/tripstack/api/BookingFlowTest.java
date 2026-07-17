package com.tripstack.api;

import com.tripstack.base.BaseTest;
import com.tripstack.model.request.BookingRequest;
import com.tripstack.model.request.FlightSearchRequest;
import com.tripstack.model.response.BookingResponse;
import com.tripstack.services.AuthService;
import com.tripstack.services.BookingService;
import com.tripstack.services.FlightService;
import com.tripstack.utils.TestDataFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookingFlowTest extends BaseTest {

    AuthService authService = new AuthService();
    FlightService flightService = new FlightService();
    BookingService bookingService = new BookingService();

    @Test
    void verifyEndToEndBookingFlow() {

        String token = authService.getToken();

        FlightSearchRequest request = TestDataFactory.flightSearch();

        Response flightResponse = flightService.searchFlights(request);

        String flightId = flightResponse.jsonPath().getString("flights[0].id");

        Response seatResponse = flightService.getSeatMap(flightId);

        String seatId = flightService.getFirstAvailableSeat(seatResponse);

        BookingRequest bookingRequest =
                TestDataFactory.bookingRequest(flightId, seatId);

        BookingResponse booking =
                bookingService.createBooking(token, bookingRequest);

        assertEquals("HELD", booking.getState());

        bookingService.payBooking(token, booking.getId())
                .then()
                .statusCode(200);

        BookingResponse confirmed =
                bookingService.confirmBooking(token, booking.getId());

        assertEquals("CONFIRMED", confirmed.getState());

        BookingResponse cancelled =
                bookingService.cancelBooking(token, booking.getId());

        assertTrue(
                cancelled.getState().equals("CANCELLED")
                        || cancelled.getState().equals("REFUNDED"));
    }
}