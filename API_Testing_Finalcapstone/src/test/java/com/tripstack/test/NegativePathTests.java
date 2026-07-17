package com.tripstack.test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.tripstack.model.BookingRequest;
import com.tripstack.model.LoginRequest;

import io.restassured.response.Response;

class NegativePathTests extends BaseTest {

    @Test
    void loginWithInvalidEmail() {
      LoginRequest loginRequest = new LoginRequest("bad@example.com", TEST_PASSWORD);

       
        Response response = authClient.loginResponse(loginRequest);

       
        assertAll(
            () -> assertTrue(response.getStatusCode() == 400 || response.getStatusCode() == 401, "Invalid email login should return an auth error"),
            () -> assertJsonSchema(response, "error-schema.json"),
            () -> assertErrorPayload(response),
            () -> assertFalse(response.asString().isBlank(), "Error response should not be blank")
        );
    }

    @Test
    void loginWithInvalidPassword() {
       
        LoginRequest loginRequest = new LoginRequest(TEST_EMAIL, "wrong-password");

     
        Response response = authClient.loginResponse(loginRequest);

     
        assertAll(
            () -> assertTrue(response.getStatusCode() == 400 || response.getStatusCode() == 401, "Invalid password login should return an auth error"),
            () -> assertJsonSchema(response, "error-schema.json"),
            () -> assertErrorPayload(response),
            () -> assertFalse(response.asString().isBlank(), "Error response should not be blank")
        );
    }

    @Test
    void loginWithEmptyCredentials() {
      
        LoginRequest loginRequest = new LoginRequest("", "");

     
        Response response = authClient.loginResponse(loginRequest);

     
        assertAll(
            () -> assertTrue(response.getStatusCode() == 400 || response.getStatusCode() == 401, "Empty credentials login should return an auth error"),
            () -> assertJsonSchema(response, "error-schema.json"),
            () -> assertErrorPayload(response),
            () -> assertFalse(response.asString().isBlank(), "Error response should not be blank")
        );
    }

    @Test
    void searchFlightsWithInvalidOrigin() {
        String travelDate = futureDate(25);

        Response response = flightClient.searchFlights("ZZZ", "HYD", travelDate, 1, "business");

        assertAll(
            () -> assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 400, "Invalid origin should return a handled response"),
            () -> assertFalse(response.asString().isBlank(), "Response body should not be blank"),
            () -> assertTrue(response.getStatusCode() != 400 || response.asString().contains("error") || response.asString().contains("message"), "Error status should contain an error payload")
        );
    }

    @Test
    void searchFlightsWithMissingMandatoryFields() {
        String travelDate = "";

        Response response = flightClient.searchFlights("MAA", "HYD", travelDate, 1, "business");

        assertAll(
            () -> assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 400, "Missing date should return a handled response"),
            () -> assertFalse(response.asString().isBlank(), "Response body should not be blank"),
            () -> assertTrue(response.getStatusCode() != 400 || response.asString().contains("error") || response.asString().contains("message"), "Error status should contain an error payload")
        );
    }

    @Test
    void searchFlightsWithPastJourneyDate() {
        String travelDate = futureDate(-5);

        Response response = flightClient.searchFlights("MAA", "HYD", travelDate, 1, "business");

        assertAll(
            () -> assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 400, "Past date should return a handled response"),
            () -> assertFalse(response.asString().isBlank(), "Response body should not be blank"),
            () -> assertTrue(response.getStatusCode() != 400 || response.asString().contains("error") || response.asString().contains("message"), "Error status should contain an error payload")
        );
    }

    @Test
    void retrieveSeatMapWithInvalidFlightId() {
        String invalidFlightId = "INVALID-FLIGHT";

        Response response = flightClient.getSeatMap(invalidFlightId);

        assertAll(
            () -> assertTrue(response.getStatusCode() == 400 || response.getStatusCode() == 404, "Invalid flight ID should return an error"),
            () -> assertJsonSchema(response, "error-schema.json"),
            () -> assertErrorPayload(response),
            () -> assertFalse(response.asString().isBlank(), "Error response should not be blank")
        );
    }

    @Test
    void createBookingWithInvalidPassengerDetails() {
        BookingRequest bookingRequest = new BookingRequest("flight", "FL-MAAHYD-51", List.of(), true, 120);

        Response response = bookingClient.createBooking(authToken, bookingRequest);

        assertAll(
            () -> assertEquals(400, response.getStatusCode(), "Invalid passenger details should return 400"),
            () -> assertJsonSchema(response, "error-schema.json"),
            () -> assertErrorPayload(response),
            () -> assertFalse(response.asString().isBlank(), "Error response should not be blank")
        );
    }

    @Test
    void createBookingWithInvalidFlight() {
        BookingRequest bookingRequest = new BookingRequest("flight", "INVALID-FLIGHT", List.of("2A"), true, 120);

        Response response = bookingClient.createBooking(authToken, bookingRequest);

        assertAll(
            () -> assertTrue(response.getStatusCode() == 400 || response.getStatusCode() == 404, "Invalid flight should return an error"),
            () -> assertJsonSchema(response, "error-schema.json"),
            () -> assertErrorPayload(response),
            () -> assertFalse(response.asString().isBlank(), "Error response should not be blank")
        );
    }

    @Test
    void paymentWithInvalidBookingId() {
        String invalidBookingId = "invalid-booking-id";

        Response response = bookingClient.payBooking(authToken, invalidBookingId);

        assertAll(
            () -> assertTrue(response.getStatusCode() == 400 || response.getStatusCode() == 404, "Invalid booking ID should return an error"),
            () -> assertJsonSchema(response, "error-schema.json"),
            () -> assertErrorPayload(response),
            () -> assertFalse(response.asString().isBlank(), "Error response should not be blank")
        );
    }

    @Test
    void confirmBookingWithInvalidBooking() {
        String invalidBookingId = "invalid-booking-id";

        Response response = bookingClient.confirmBooking(authToken, invalidBookingId);

        assertAll(
            () -> assertTrue(response.getStatusCode() == 400 || response.getStatusCode() == 404, "Invalid booking should return an error"),
            () -> assertJsonSchema(response, "error-schema.json"),
            () -> assertErrorPayload(response),
            () -> assertFalse(response.asString().isBlank(), "Error response should not be blank")
        );
    }

    @Test
    void retrieveBookingWithInvalidId() {
        String invalidPnr = "invalid-pnr";

        Response response = bookingClient.getBookingByPnr(authToken, invalidPnr);

        assertAll(
            () -> assertTrue(response.getStatusCode() == 400 || response.getStatusCode() == 404, "Invalid booking ID should return an error"),
            () -> assertJsonSchema(response, "error-schema.json"),
            () -> assertErrorPayload(response),
            () -> assertFalse(response.asString().isBlank(), "Error response should not be blank")
        );
    }

    @Test
    void cancelBookingWithInvalidId() {
        String invalidBookingId = "invalid-booking-id";

        Response response = bookingClient.cancelBooking(authToken, invalidBookingId);

        assertAll(
            () -> assertTrue(response.getStatusCode() == 400 || response.getStatusCode() == 404, "Invalid booking ID should return an error"),
            () -> assertJsonSchema(response, "error-schema.json"),
            () -> assertErrorPayload(response),
            () -> assertFalse(response.asString().isBlank(), "Error response should not be blank")
        );
    }
}
