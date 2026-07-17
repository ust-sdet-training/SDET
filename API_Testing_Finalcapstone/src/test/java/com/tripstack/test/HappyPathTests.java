package com.tripstack.test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.tripstack.model.BookingRequest;
import com.tripstack.model.BookingResponse;
import com.tripstack.model.LoginRequest;
import com.tripstack.model.LoginResponse;

import io.restassured.response.Response;

class HappyPathTests extends BaseTest {

    @Test
    void loginWithValidCredentials() throws Exception {
        LoginRequest loginRequest = new LoginRequest(TEST_EMAIL, TEST_PASSWORD);

        Response loginHttpResponse = authClient.loginResponse(loginRequest);
        LoginResponse loginResponse = loginHttpResponse.as(LoginResponse.class);
        Response identityResponse = authClient.meResponse(loginResponse.getToken());

        assertAll(
            () -> assertEquals(200, loginHttpResponse.getStatusCode(), "Login call should return 200"),
            () -> assertJsonSchema(loginHttpResponse, "login-schema.json"),
            () -> assertEquals(200, identityResponse.getStatusCode(), "Login identity call should return 200"),
            () -> assertJsonSchema(identityResponse, "profile-schema.json"),
            () -> assertNotNull(loginResponse, "Login response should not be null"),
            () -> assertTrue(loginResponse.getToken() != null && !loginResponse.getToken().isBlank(), "Login token should be present"),
            () -> assertTrue(loginResponse.getEmployeeId() != null && !loginResponse.getEmployeeId().isBlank(), "Login response should include the employee id"),
            () -> assertRequiredString(identityResponse, "empId", "empId"),
            () -> assertRequiredString(identityResponse, "email", "email"),
            () -> assertFalse(identityResponse.asString().isBlank(), "Identity response should not be blank")
        );
    }

    @Test
    void searchFlightsSuccessfully() throws Exception {
        String travelDate = futureDate(25);

        Response response = flightClient.searchFlights("MAA", "HYD", travelDate, 1, "business");

        assertAll(
            () -> assertEquals(200, response.getStatusCode(), "Flight search should return 200"),
            () -> assertJsonSchema(response, "flight-search-schema.json"),
            () -> assertTrue(response.jsonPath().getInt("count") > 0, "At least one flight should be returned"),
            () -> assertFalse(response.jsonPath().getList("flights").isEmpty(), "At least one flight should be returned"),
            () -> assertNotNull(response.jsonPath().getString("flights[0].id"), "Flight ID should be present")
        );
    }

    @Test
    void retrieveSeatMapSuccessfully() throws Exception {
        String flightId = "FL-MAAHYD-51";

        Response response = flightClient.getSeatMap(flightId);

        assertAll(
            () -> assertEquals(200, response.getStatusCode(), "Seat map request should return 200"),
            () -> assertJsonSchema(response, "seat-map-schema.json"),
            () -> assertRequiredString(response, "flight_id", "flight_id"),
            () -> assertTrue(response.jsonPath().getInt("total") > 0, "Seat map should contain seats"),
            () -> assertFalse(response.asString().isBlank(), "Seat map response should not be blank")
        );
    }

    @Test
    void createBookingSuccessfully() throws Exception {
        BookingRequest bookingRequest = buildBookingRequest("FL-MAAHYD-51", nextSeatId());

        Response response = bookingClient.createBooking(authToken, bookingRequest);
        BookingResponse bookingResponse = response.as(BookingResponse.class);

        assertAll(
            () -> assertEquals(201, response.getStatusCode(), "Booking creation should return 201"),
            () -> assertJsonSchema(response, "booking-schema.json"),
            () -> assertNotNull(bookingResponse, "Booking response should not be null"),
            () -> assertNotNull(bookingResponse.getId(), "Booking ID should be present"),
            () -> assertNotNull(bookingResponse.getInventoryId(), "Inventory ID should be present"),
            () -> assertNotNull(bookingResponse.getJourneyType(), "Journey type should be present"),
            () -> assertEquals("HELD", bookingResponse.getState(), "Booking should be held on creation")
        );

        assertBookingPersistedThroughApi(bookingResponse.getId(), response.jsonPath().getString("pnr"), response.jsonPath().getString("state"));
    }

    @Test
    void paymentShouldBeSuccessful() throws Exception {
        Response createResponse = bookingClient.createBooking(authToken, buildBookingRequest("FL-MAAHYD-51", nextSeatId()));
        BookingResponse bookingResponse = createResponse.as(BookingResponse.class);

        Response response = bookingClient.payBooking(authToken, bookingResponse.getId());

        assertAll(
            () -> assertEquals(200, response.getStatusCode(), "Payment should return 200"),
            () -> assertJsonSchema(response, "booking-schema.json"),
            () -> assertEquals("PAYMENT_PENDING", response.jsonPath().getString("state"), "Payment should move the booking to PAYMENT_PENDING"),
            () -> assertFalse(response.asString().isBlank(), "Payment response should not be blank")
        );

        assertBookingPersistedThroughApi(bookingResponse.getId(), response.jsonPath().getString("pnr"), response.jsonPath().getString("state"));
    }

    @Test
    void confirmBookingSuccessfully() throws Exception {
        Response createResponse = bookingClient.createBooking(authToken, buildBookingRequest("FL-MAAHYD-51", nextSeatId()));
        BookingResponse bookingResponse = createResponse.as(BookingResponse.class);

        bookingClient.payBooking(authToken, bookingResponse.getId());
        Response response = bookingClient.confirmBooking(authToken, bookingResponse.getId());

        assertAll(
            () -> assertEquals(200, response.getStatusCode(), "Booking confirmation should return 200"),
            () -> assertJsonSchema(response, "booking-schema.json"),
            () -> assertEquals("CONFIRMED", response.jsonPath().getString("state"), "Booking should be confirmed")
        );

        assertBookingPersistedThroughApi(bookingResponse.getId(), response.jsonPath().getString("pnr"), response.jsonPath().getString("state"));
    }

    @Test
    void retrieveBookingSuccessfully() throws Exception {

        Response createResponse =
                bookingClient.createBooking(
                        authToken,
                        buildBookingRequest("FL-MAAHYD-51", nextSeatId()));

        assertEquals(201, createResponse.getStatusCode());

        BookingResponse bookingResponse =
                createResponse.as(BookingResponse.class);

        Response paymentResponse =
                bookingClient.payBooking(
                        authToken,
                        bookingResponse.getId());

        assertEquals(200, paymentResponse.getStatusCode());

        Response confirmResponse =
                bookingClient.confirmBooking(
                        authToken,
                        bookingResponse.getId());

        assertEquals(200, confirmResponse.getStatusCode());

        String pnr =
                confirmResponse.jsonPath().getString("pnr");

        Response response =
                bookingClient.getBookingByPnr(
                        authToken,
                        pnr);

        assertAll(
                () -> assertEquals(200, response.getStatusCode()),
                () -> assertJsonSchema(response, "booking-schema.json"),
                () -> assertEquals(
                        bookingResponse.getId(),
                        response.jsonPath().getString("id"))
        );
    }
    @Test
    void cancelBookingSuccessfully() throws Exception {
        Response createResponse = bookingClient.createBooking(authToken, buildBookingRequest("FL-MAAHYD-51", nextSeatId()));
        BookingResponse bookingResponse = createResponse.as(BookingResponse.class);

        Response response = bookingClient.cancelBooking(authToken, bookingResponse.getId());

        assertAll(
            () -> assertEquals(200, response.getStatusCode(), "Booking cancellation should return 200"),
            () -> assertJsonSchema(response, "booking-schema.json"),
            () -> assertEquals("REFUNDED", response.jsonPath().getString("state"), "Booking should be marked as refunded")
        );

        assertBookingPersistedThroughApi(bookingResponse.getId(), response.jsonPath().getString("pnr"), response.jsonPath().getString("state"));
    }
}
