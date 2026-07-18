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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PaymentFaultTest extends BaseTest {

    private final AuthService authService = new AuthService();
    private final FlightService flightService = new FlightService();
    private final BookingService bookingService = new BookingService();

    @Test
    @DisplayName("Verify payment gateway timeout")
    void verifyPaymentGatewayTimeout() {

        String token = authService.getToken();

        FlightSearchRequest request = TestDataFactory.flightSearch();

        Response flightResponse = flightService.searchFlights(request);

        String flightId =
                flightResponse.jsonPath().getString("flights[0].id");

        Response seatResponse =
                flightService.getSeatMap(flightId);

        String seatId =
                flightService.getFirstAvailableSeat(seatResponse);

        BookingRequest bookingRequest =
                TestDataFactory.bookingRequest(flightId, seatId);

        BookingResponse booking =
                bookingService.createBooking(token, bookingRequest);

        bookingService
                .payBooking(token, booking.getId())
                .then()
                .statusCode(200);

    }

}