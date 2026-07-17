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

public class BookingTest extends BaseTest {

    AuthService authService = new AuthService();
    FlightService flightService = new FlightService();
    BookingService bookingService = new BookingService();

    @Test
    void bookingFlowTest() {

        // Login
        String token = authService.getToken();

        // Search Flight
        FlightSearchRequest searchRequest = TestDataFactory.flightSearch();

        Response flightResponse = flightService.searchFlights(searchRequest);

        String flightId =
                flightResponse.jsonPath().getString("flights[0].id");

        // Seat Map
        Response seatResponse = flightService.getSeatMap(flightId);

        String seatId =
                flightService.getFirstAvailableSeat(seatResponse);

        // Create Booking
        BookingRequest bookingRequest =
                TestDataFactory.bookingRequest(flightId, seatId);

        BookingResponse booking =
                bookingService.createBooking(token, bookingRequest);

        assertEquals("HELD", booking.getState());

        // Pay
        Response payment =
                bookingService.payBooking(token, booking.getId());

        payment.then().statusCode(200);

        // Confirm
        BookingResponse confirmed =
                bookingService.confirmBooking(token, booking.getId());

        assertEquals("CONFIRMED", confirmed.getState());

        assertNotNull(confirmed.getPnr());

        // Get Booking
        BookingResponse bookingByPnr =
                bookingService.getBookingByPnr(token, confirmed.getPnr());

        assertEquals(
                confirmed.getPnr(),
                bookingByPnr.getPnr());

        // Cancel
        BookingResponse cancelled =
                bookingService.cancelBooking(token, booking.getId());

        assertTrue(
                cancelled.getState().equals("CANCELLED") ||
                        cancelled.getState().equals("REFUNDED"));

    }
}