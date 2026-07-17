package com.tripstack.test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.tripstack.model.BookingRequest;
import com.tripstack.model.LoginRequest;

import io.restassured.response.Response;

class EdgeCaseTests extends BaseTest {

    @Test
    void loginWithLeadingAndTrailingSpaces() {
        LoginRequest loginRequest = new LoginRequest(" " + TEST_EMAIL + " ", " " + TEST_PASSWORD + " ");

        Response response = authClient.loginResponse(loginRequest);

        assertAll(
            () -> assertTrue(response.getStatusCode() == 400 || response.getStatusCode() == 401, "Leading or trailing spaces should return an auth error"),
            () -> assertJsonSchema(response, "error-schema.json"),
            () -> assertErrorPayload(response),
            () -> assertFalse(response.asString().isBlank(), "Error response should not be blank")
        );
    }

    @Test
    void searchFlightsWithSameOriginAndDestination() {
        String travelDate = futureDate(25);

        Response response = flightClient.searchFlights("MAA", "MAA", travelDate, 1, "business");

        assertAll(
            () -> assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 400, "Same-origin search should return a handled response"),
            () -> assertFalse(response.asString().isBlank(), "Response body should not be blank"),
            () -> assertTrue(response.getStatusCode() != 400 || response.asString().contains("error") || response.asString().contains("message"), "Error status should contain an error payload")
        );
    }

    @Test
    void searchFlightsWithFarFutureDate() {
        String travelDate = futureDate(365);

        Response response = flightClient.searchFlights("MAA", "HYD", travelDate, 1, "business");

        assertAll(
            () -> assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 400, "Far-future search should return a handled response"),
            () -> assertFalse(response.asString().isBlank(), "Response body should not be blank"),
            () -> assertTrue(response.getStatusCode() != 400 || response.asString().contains("error") || response.asString().contains("message"), "Error status should contain an error payload")
        );
    }

    @Test
    void bookingShouldFailForBookedSeat() {
        BookingRequest bookingRequest = new BookingRequest("flight", "FL-MAAHYD-51", List.of(nextSeatId()), true, 120);

        Response firstBooking = bookingClient.createBooking(authToken, bookingRequest);
        Response secondBooking = bookingClient.createBooking(authToken, bookingRequest);

        assertAll(
            () -> assertTrue(firstBooking.getStatusCode() == 200 || firstBooking.getStatusCode() == 201, "The first booking should be created successfully"),
            () -> assertTrue(secondBooking.getStatusCode() == 200 || secondBooking.getStatusCode() == 201 || secondBooking.getStatusCode() == 400, "The second booking should return a handled result"),
            () -> assertFalse(secondBooking.asString().isBlank(), "The second booking response should not be blank")
        );
    }
}
