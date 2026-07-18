package com.capstone.tests;

import com.capstone.api.BookingApiClient;
import com.capstone.api.FlightApiClient;
import com.capstone.support.BaseApiTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;

public class BookingApiTest extends BaseApiTest {

    private FlightApiClient flightApiClient;
    private BookingApiClient bookingApiClient;

    @BeforeEach
    void initClients() {
        flightApiClient = new FlightApiClient(request);
        bookingApiClient = new BookingApiClient(request);
    }

    @Test
    void FlightBookingTest() {

        String token = loginAs("traveller");
        assertEquals(200, bookingApiClient.resetNamespace(token).statusCode());

        Response flightSearchResponse = flightApiClient.searchFlights(
                "CCU", "BOM", "2026-08-10", 1, "economy");
        flightSearchResponse.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/flight-search-schema.json"));

        String flightId = flightApiClient.selectFirstFlightId(
                "CCU", "BOM", "2026-08-10", 1, "economy");

        Response seatMapResponse = flightApiClient.getSeatMap(flightId);
        seatMapResponse.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/flight-seat-map-schema.json"));

        List<String> seatIds = flightApiClient.selectAvailableSeatIds(flightId, "economy");

        Response holdResponse = bookingApiClient.holdAvailableSeat(token, flightId, seatIds);

        String bookingId = holdResponse.jsonPath().getString("id");

        assertEquals(201, holdResponse.statusCode());
        holdResponse.then().body(matchesJsonSchemaInClasspath("schemas/booking-schema.json"));
        assertEquals("HELD", holdResponse.jsonPath().getString("state"));

        Response payResponse = bookingApiClient.payBooking(token, bookingId);

        assertEquals(200, payResponse.statusCode());
        payResponse.then().body(matchesJsonSchemaInClasspath("schemas/booking-schema.json"));
        assertEquals("PAYMENT_PENDING", payResponse.jsonPath().getString("state"));

        Response confirmResponse = bookingApiClient.confirmBooking(token, bookingId);

        assertEquals(200, confirmResponse.statusCode());
        confirmResponse.then().body(matchesJsonSchemaInClasspath("schemas/booking-schema.json"));
        assertEquals("CONFIRMED", confirmResponse.jsonPath().getString("state"));

        Response bookingsResponse = bookingApiClient.getBookings(token);

        assertEquals(200, bookingsResponse.statusCode());
        bookingsResponse.then().body(matchesJsonSchemaInClasspath("schemas/bookings-schema.json"));
        List<String> bookingIds = bookingsResponse.jsonPath().getList("id");
        assertTrue(bookingIds.contains(bookingId));
    }

    @Test
    @Tag("negative")
    void SeatHoldHasExpired() throws InterruptedException {
        final int holdTtlSeconds = 1;
        String token = loginAs("otherTraveller");
        assertEquals(200, bookingApiClient.resetNamespace(token).statusCode());
        String flightId = flightApiClient.selectFirstFlightId(
                "CCU", "BOM", "2026-08-10", 1, "economy");
        List<String> availableSeatIds = flightApiClient.selectAvailableSeatIds(flightId, "economy");

        Response holdResponse = bookingApiClient.holdAvailableSeat(
                token, flightId, availableSeatIds, holdTtlSeconds);
        assertEquals(201, holdResponse.statusCode());

        Thread.sleep((holdTtlSeconds + 1L) * 1_000L);

        Response paymentResponse = bookingApiClient.payBooking(
                token, holdResponse.jsonPath().getString("id"));

        assertAll(
                () -> assertEquals(409, paymentResponse.statusCode()),
                () -> assertEquals("HOLD_EXPIRED", paymentResponse.jsonPath().getString("error"))
        );
    }
}
