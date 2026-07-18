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

    public static String bookingId;

    AuthService authService = new AuthService();
    FlightService flightService = new FlightService();
    BookingService bookingService = new BookingService();

    @Test
    void bookingFlowTest() {

        String token = authService.getToken();

        FlightSearchRequest searchRequest =
                TestDataFactory.flightSearch();

        System.out.println("FROM = " + searchRequest.getFrom());
        System.out.println("TO = " + searchRequest.getTo());
        System.out.println("DATE = " + searchRequest.getDate());
        System.out.println("PAX = " + searchRequest.getPassengers());
        System.out.println("CLASS = " + searchRequest.getTravelClass());

        Response flightResponse =
                flightService.searchFlights(searchRequest);
        flightResponse.prettyPrint();

        String flightId =
                flightService.getFirstFlightId(flightResponse);

        System.out.println("Flight ID = " + flightId);

        Response seatResponse =
                flightService.getSeatMap(flightId);

        String seatId =
                flightService.getFirstAvailableSeat(seatResponse);

        System.out.println("Seat ID = " + seatId);

        BookingRequest bookingRequest =
                TestDataFactory.bookingRequest(flightId, seatId);

        BookingResponse booking =
                bookingService.createBooking(token, bookingRequest);

        bookingId = booking.getId();

        assertNotNull(bookingId);

        assertEquals("HELD", booking.getState());

        Response payment =
                bookingService.payBooking(token, bookingId);

        payment.then().statusCode(200);

        BookingResponse confirmed =
                bookingService.confirmBooking(token, bookingId);

        assertEquals("CONFIRMED", confirmed.getState());

        assertNotNull(confirmed.getPnr());

        BookingResponse bookingByPnr =
                bookingService.getBookingByPnr(
                        token,
                        confirmed.getPnr()
                );

        assertEquals(
                confirmed.getPnr(),
                bookingByPnr.getPnr()
        );

        BookingResponse cancelled =
                bookingService.cancelBooking(
                        token,
                        bookingId
                );

        assertTrue(
                cancelled.getState().equals("CANCELLED") ||
                        cancelled.getState().equals("REFUNDED")
        );

    }

}