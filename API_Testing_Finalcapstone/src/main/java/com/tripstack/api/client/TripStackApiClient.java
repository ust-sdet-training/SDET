package com.tripstack.api.client;

import com.tripstack.api.specs.AuthRequestSpec;
import com.tripstack.api.specs.BookingRequestSpec;
import com.tripstack.api.specs.FlightRequestSpec;
import com.tripstack.config.TestConfig;
import com.tripstack.models.BookingResponse;
import com.tripstack.models.FlightResponse;
import io.restassured.response.Response;

import java.util.Map;

public class TripStackApiClient {
    public TripStackApiClient() {
    }

    public TripStackApiClient(String baseUri) {
        TestConfig.setBaseUrl(baseUri);
    }

    public Response getFlights() {
        return searchFlights("MAA", "HYD", "2026-07-25", 1, "business");
    }

    public Response createBooking(String flightId, String passengerName) {
        return createBooking("", flightId, passengerName, new String[]{"12A"}, true);
    }

    public Response login(String email, String password) {
        return AuthRequestSpec.publicSpec()
                .body(Map.of("email", email, "password", password))
                .when()
                .post("/auth/login");
    }

    public Response currentIdentity(String token) {
        return AuthRequestSpec.authenticatedSpec(token)
                .when()
                .get("/auth/me");
    }

    public Response searchFlights(String from, String to, String date, int pax, String cabinClass) {
        return FlightRequestSpec.publicSpec()
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("date", date)
                .queryParam("pax", pax)
                .queryParam("class", cabinClass)
                .when()
                .get("/flights");
    }

    public Response getFlightSeats(String flightId) {
        return FlightRequestSpec.publicSpec()
                .when()
                .get("/flights/{id}/seats", flightId);
    }

    public Response createBooking(String token, String journeyType, String inventoryId, String[] seatIds, boolean refundable) {
        return BookingRequestSpec.authenticatedSpec(token)
                .body(Map.of(
                        "journeyType", journeyType,
                        "inventoryId", inventoryId,
                        "seatIds", seatIds,
                        "refundable", refundable,
                        "holdTtlSec", 120
                ))
                .when()
                .post("/bookings");
    }

    public Response createBooking(String token, String inventoryId, String passengerName, String[] seatIds, boolean refundable) {
        return createBooking(token, "flight", inventoryId, seatIds, refundable);
    }

    public Response payBooking(String token, String bookingId) {
        return BookingRequestSpec.authenticatedSpec(token)
                .body(Map.of())
                .when()
                .post("/bookings/{id}/pay", bookingId);
    }

    public Response confirmBooking(String token, String bookingId) {
        return BookingRequestSpec.authenticatedSpec(token)
                .when()
                .post("/bookings/{id}/confirm", bookingId);
    }

    public Response listBookings(String token) {
        return BookingRequestSpec.authenticatedSpec(token)
                .when()
                .get("/bookings");
    }

    public Response getBookingByPnr(String token, String pnr) {
        return BookingRequestSpec.authenticatedSpec(token)
                .when()
                .get("/bookings/{pnr}", pnr);
    }

    public Response resetNamespace(String token) {
        return BookingRequestSpec.authenticatedSpec(token)
                .when()
                .post("/reset");
    }
}
