package com.tripstack.db;

import com.tripstack.base.BaseTest;
import com.tripstack.database.DBUtils;
import com.tripstack.model.request.BookingRequest;
import com.tripstack.model.request.FlightSearchRequest;
import com.tripstack.model.response.BookingResponse;
import com.tripstack.services.AuthService;
import com.tripstack.services.BookingService;
import com.tripstack.services.FlightService;
import com.tripstack.utils.TestDataFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingDBTest extends BaseTest {

    private final AuthService authService = new AuthService();
    private final FlightService flightService = new FlightService();
    private final BookingService bookingService = new BookingService();

    @Test
    void verifyBookingExistsInDatabase() {

        String token = authService.getToken();

        FlightSearchRequest searchRequest = TestDataFactory.flightSearch();

        Response flightResponse =
                flightService.searchFlights(searchRequest);

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

        int count = DBUtils.getCount(
                "SELECT COUNT(*) FROM bookings WHERE id = ?",
                booking.getId());

        assertEquals(1, count);

    }

}