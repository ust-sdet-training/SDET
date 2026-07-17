package com.tripstack.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.tripstack.model.BookingResponse;

import io.restassured.response.Response;

class BookingFlowTest extends BaseTest {

    @Test
    void completeBookingFlowFromLoginToCancellation() throws Exception {
        String travelDate = futureDate(25);
        String flightId = "FL-MAAHYD-51";
        String seatId = nextSeatId();

        Response searchResponse = flightClient.searchFlights("MAA", "HYD", travelDate, 1, "business");
        Response seatMapResponse = flightClient.getSeatMap(flightId);
        Response createResponse = bookingClient.createBooking(authToken, buildBookingRequest(flightId, seatId));
        BookingResponse bookingResponse = createResponse.as(BookingResponse.class);
        Response paymentResponse = bookingClient.payBooking(authToken, bookingResponse.getId());
        Response confirmResponse = bookingClient.confirmBooking(authToken, bookingResponse.getId());
        String confirmedPnr = confirmResponse.jsonPath().getString("pnr");
        Response retrieveResponse = bookingClient.getBookingByPnr(authToken, confirmedPnr);
        Response cancelResponse = bookingClient.cancelBooking(authToken, bookingResponse.getId());

        assertEquals(200, searchResponse.getStatusCode(), "Flight search should return 200");
        assertEquals(200, seatMapResponse.getStatusCode(), "Seat map retrieval should return 200");
        assertEquals(201, createResponse.getStatusCode(), "Booking creation should return 201");
        assertEquals(200, paymentResponse.getStatusCode(), "Payment should return 200");
        assertEquals(200, confirmResponse.getStatusCode(), "Confirmation should return 200");
        assertEquals(200, retrieveResponse.getStatusCode(), "Retrieve booking should return 200");
        assertEquals(200, cancelResponse.getStatusCode(), "Cancellation should return 200");

        assertJsonSchema(searchResponse, "flight-search-schema.json");
        assertJsonSchema(seatMapResponse, "seat-map-schema.json");
        assertJsonSchema(createResponse, "booking-schema.json");
        assertJsonSchema(paymentResponse, "payment-schema.json");
        assertJsonSchema(confirmResponse, "confirmation-schema.json");
        assertJsonSchema(retrieveResponse, "retrieve-booking-schema.json");
        assertJsonSchema(cancelResponse, "cancellation-schema.json");

        assertNotNull(bookingResponse.getId(), "Booking ID should be present");
        assertNotNull(bookingResponse.getState(), "Booking state should be present");
        assertNotNull(confirmedPnr, "Confirmation should create a booking PNR");

        assertBookingPersistedThroughApi(bookingResponse.getId(), confirmedPnr, "REFUNDED");
    }
}
