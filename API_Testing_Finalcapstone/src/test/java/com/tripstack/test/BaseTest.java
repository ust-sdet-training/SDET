package com.tripstack.test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;

import com.tripstack.client.AuthClient;
import com.tripstack.client.BookingClient;
import com.tripstack.client.FlightClient;
import com.tripstack.database.BookingRepository;
import com.tripstack.model.BookingRequest;
import com.tripstack.model.BookingResponse;
import com.tripstack.model.LoginRequest;
import com.tripstack.model.LoginResponse;
import com.tripstack.support.TestValidationHelper;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.response.Response;

public class BaseTest {
    protected static AuthClient authClient;
    protected static FlightClient flightClient;
    protected static BookingClient bookingClient;
    protected static String authToken;

    protected static final String TEST_EMAIL = "grace@tripstack.test";
    protected static final String TEST_PASSWORD = "Password@123";

    private static final TestValidationHelper validationHelper = new TestValidationHelper();
    private static final AtomicInteger BOOKING_SEAT_INDEX = new AtomicInteger(0);
    private static final String[] BOOKING_SEATS = {"2E", "2F", "2G", "2H", "2I", "2J"};

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

    protected void assertBookingPersistedInDatabase(String bookingId, String expectedPnr, String expectedState) {
        BookingRepository repository = new BookingRepository();
        BookingResponse storedBooking = repository.findBookingById(bookingId);

        assertNotNull(storedBooking, "Booking should be persisted in the database");
        assertEquals(expectedPnr, storedBooking.getPnr(), "Stored booking PNR should match the API response");
        assertEquals(expectedState, storedBooking.getState(), "Stored booking state should match the API response");
    }

    protected void assertDatabaseHealth() throws Exception {
        validationHelper.assertDatabaseIsAvailable();
    }

    protected String futureDate(int daysAhead) {
        return LocalDate.now().plusDays(daysAhead).format(DateTimeFormatter.ISO_DATE);
    }

    protected BookingRequest buildBookingRequest(String inventoryId, String seatId) {
        return new BookingRequest("flight", inventoryId, List.of(seatId), true, 120);
    }

    protected String nextSeatId() {
        int index = BOOKING_SEAT_INDEX.getAndIncrement() % BOOKING_SEATS.length;
        return BOOKING_SEATS[index];
    }
}
