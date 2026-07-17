package org.sdet.tests.booking;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.sdet.base.BaseTest;
import org.sdet.builders.BookingBuilder;
import org.sdet.clients.PaymentClient;
import org.sdet.model.request.BookingRequest;

import static org.junit.jupiter.api.Assertions.*;

public class BookingLifeCycleTests extends BaseTest {

    @Test
    public void verifyBookingLifecycle() {

        // Step 1 : Search Flight
        Response searchResponse = searchClient.searchFlights(
                "BOM",
                "MAA",
                "2026-07-25"
        );

        searchResponse.then().statusCode(200);

        String tripId = searchResponse.jsonPath()
                .getString("flights[0].id");   // Replace with actual JSON path

        assertNotNull(tripId);

        // Step 2 : Create Booking
        BookingRequest bookingRequest = new BookingBuilder()
                .setTripId(tripId)
                .setSeatNumber("12A")
                .setPassengerName("Salman Benny")
                .build();

        Response bookingResponse = bookingClient.createBooking(bookingRequest);

        bookingResponse.then().statusCode(201);

        String bookingId = bookingResponse.jsonPath()
                .getString("bookingId");

        String pnr = bookingResponse.jsonPath()
                .getString("pnr");

        assertNotNull(bookingId);
        assertNotNull(pnr);
        // Step 3 : Pay
        Response paymentResponse = paymentClient.payBooking(bookingId);

        paymentResponse.then().statusCode(200);

        // Step 4 : Confirm
        Response confirmResponse = bookingClient.confirmBooking(bookingId);

        confirmResponse.then().statusCode(200);

        // Step 5 : Get Booking
        Response bookingDetails = bookingClient.getBookingByPNR(pnr);

        bookingDetails.then().statusCode(200);

        assertEquals(
                pnr,
                bookingDetails.jsonPath().getString("pnr")
        );

        // Step 6 : Cancel
        Response cancelResponse = bookingClient.cancelBooking(bookingId);

        cancelResponse.then().statusCode(200);

    }
}