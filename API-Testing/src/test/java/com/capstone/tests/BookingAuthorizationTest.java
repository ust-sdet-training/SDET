package com.capstone.tests;

import com.capstone.api.BookingApiClient;
import com.capstone.api.FlightApiClient;
import com.capstone.support.BaseApiTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BookingAuthorizationTest extends BaseApiTest {

    private FlightApiClient flightApiClient;
    private BookingApiClient bookingApiClient;

    @BeforeEach
    void initClients() {
        flightApiClient = new FlightApiClient(request);
        bookingApiClient = new BookingApiClient(request);
    }

    @Test
    @Tag("security")
    void ReadingAnotherTraveller() {
        String bookingOwnerToken = loginAs("otherTraveller");
        String attackerToken = loginAs("traveller");

        String flightId = flightApiClient.selectFirstFlightId(
                "CCU", "BOM", "2026-08-10", 1, "economy");
        List<String> availableSeatIds = flightApiClient.selectAvailableSeatIds(flightId, "economy");

        Response holdResponse = bookingApiClient.holdAvailableSeat(bookingOwnerToken, flightId, availableSeatIds);
        assertEquals(201, holdResponse.statusCode());

        String bookingId = holdResponse.jsonPath().getString("id");
        assertEquals(200, bookingApiClient.payBooking(bookingOwnerToken, bookingId).statusCode());

        Response confirmationResponse = bookingApiClient.confirmBooking(bookingOwnerToken, bookingId);
        assertEquals(200, confirmationResponse.statusCode());
        String ownerPnr = confirmationResponse.jsonPath().getString("pnr");
        assertFalse(ownerPnr.isBlank(), "The owner booking must have a PNR before attempting the BOLA read");

        Response unauthorizedReadResponse = bookingApiClient.getBookingByPnr(attackerToken, ownerPnr);

        assertAll(
                () -> assertEquals(403, unauthorizedReadResponse.statusCode()),
                () -> assertEquals("CROSS_NAMESPACE", unauthorizedReadResponse.jsonPath().getString("error"))
        );
    }
}
