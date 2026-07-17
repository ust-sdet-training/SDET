package com.tripstack.test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;

import com.tripstack.client.AuthClient;
import com.tripstack.client.BookingClient;
import com.tripstack.client.FlightClient;
import com.tripstack.model.BookingRequest;
import com.tripstack.model.LoginRequest;
import com.tripstack.model.LoginResponse;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.response.Response;

public class BaseTest {
    protected static AuthClient authClient;
    protected static FlightClient flightClient;
    protected static BookingClient bookingClient;
    protected static String authToken;

    protected static final String TEST_EMAIL = "grace@tripstack.test";
    protected static final String TEST_PASSWORD = "Password@123";

    protected String nextSeatId() {

        Response response = flightClient.getSeatMap("FL-MAAHYD-51");

        List<List<java.util.Map<String, Object>>> rows =
                response.jsonPath().getList("rows.seats");

        for (List<java.util.Map<String, Object>> seatRow : rows) {

            for (java.util.Map<String, Object> seat : seatRow) {

                Boolean occupied = (Boolean) seat.get("occupied");

                if (Boolean.FALSE.equals(occupied)) {
                    return seat.get("seat_id").toString();
                }
            }
        }

        throw new RuntimeException("No available seats found");
    }

    @BeforeAll
    static void setup() {
        authClient = new AuthClient();
        flightClient = new FlightClient();
        bookingClient = new BookingClient();

        LoginResponse loginResponse = authClient.login(new LoginRequest(TEST_EMAIL, TEST_PASSWORD));
        authToken = loginResponse.getToken();
    }

    protected void assertStatusCode(Response response, int expectedStatusCode, String description) {
        assertEquals(expectedStatusCode, response.getStatusCode(), description + " returned an unexpected status code");
    }

    protected void assertJsonSchema(Response response, String schemaFileName) {
        response.then().body(matchesJsonSchemaInClasspath("schemas/" + schemaFileName));
    }

    protected void assertSuccessfulResponse(Response response, String schemaFileName, int expectedStatusCode) {
        assertStatusCode(response, expectedStatusCode, "Successful API response");
        assertJsonSchema(response, schemaFileName);
        assertFalse(response.asString().isBlank(), "Successful API response body should not be blank");
    }

    protected void assertErrorResponse(Response response, String schemaFileName, int expectedStatusCode) {
        assertStatusCode(response, expectedStatusCode, "Error API response");
        assertJsonSchema(response, schemaFileName);
        assertFalse(response.asString().isBlank(), "Error API response body should not be blank");
    }

    protected void assertRequiredString(Response response, String jsonPath, String fieldName) {
        String value = response.jsonPath().getString(jsonPath);
        assertNotNull(value, fieldName + " should be present in the response body");
        assertFalse(value.isBlank(), fieldName + " should not be blank");
    }

    protected void assertErrorPayload(Response response) {
        String message = response.jsonPath().getString("message");
        String error = response.jsonPath().getString("error");
        assertFalse((message == null || message.isBlank()) && (error == null || error.isBlank()), "Error response should contain an error or message");
        assertFalse(response.asString().isBlank(), "Error response should not be blank");
    }

    protected void assertBookingPersistedThroughApi(String bookingId, String expectedPnr, String expectedState) {
        Response listResponse = bookingClient.listBookings(authToken);
        assertStatusCode(listResponse, 200, "Booking list persistence check");

        List<Map<String, Object>> bookings = listResponse.jsonPath().getList("$");
        Map<String, Object> storedBooking = bookings.stream()
                .filter(booking -> bookingId.equals(booking.get("id")))
                .findFirst()
                .orElse(null);

        assertNotNull(storedBooking, "Booking should be retrievable from the API after persistence");
        assertEquals(expectedPnr, storedBooking.get("pnr"), "Persisted booking PNR should match the API response");
        assertEquals(expectedState, storedBooking.get("state"), "Persisted booking state should match the API response");
    }

    protected String futureDate(int daysAhead) {
        return LocalDate.now().plusDays(daysAhead).format(DateTimeFormatter.ISO_DATE);
    }

    protected BookingRequest buildBookingRequest(String inventoryId, String seatId) {
        return new BookingRequest("flight", inventoryId, List.of(seatId), true, 120);
    }

  
}
