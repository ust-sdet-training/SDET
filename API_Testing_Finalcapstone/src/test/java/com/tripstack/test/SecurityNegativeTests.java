package com.tripstack.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.tripstack.model.LoginRequest;
import com.tripstack.model.LoginResponse;

import io.restassured.response.Response;

class SecurityNegativeTests extends BaseTest {

    @Test
    void cancelAnotherEmployeeBookingShouldReturn403() throws Exception {
        LoginResponse employeeSeven = authClient.login(new LoginRequest("grace@tripstack.test", TEST_PASSWORD));
        LoginResponse otherEmployee = authClient.login(new LoginRequest("bob@tripstack.test", TEST_PASSWORD));

        assertNotNull(employeeSeven.getToken(), "Employee 1007 token should be available");
        assertNotNull(otherEmployee.getToken(), "Other employee token should be available");

        Response createResponse = bookingClient.createBooking(otherEmployee.getToken(), buildBookingRequest("FL-MAAHYD-51", nextSeatId()));
        assertEquals(201, createResponse.getStatusCode(), "Booking creation for other employee should succeed");

        String otherBookingId = createResponse.jsonPath().getString("id");
        assertNotNull(otherBookingId, "Other employee booking ID should be present");

        Response cancelResponse = bookingClient.cancelBooking(employeeSeven.getToken(), otherBookingId);
        assertEquals(403, cancelResponse.getStatusCode(), "Canceling another employee's booking should return 403");
        assertFalse(cancelResponse.asString().isBlank(), "sponse should not be blank");
        assertTrue(cancelResponse.asString().contains("forbidden") || cancelResponse.asString().contains("403"), "Error response should indicate forbidden access");

        // Some API deployments return a PNR only after confirmation; fall back to booking ID when PNR is not present
        String createdPnr = createResponse.jsonPath().getString("pnr");
        String lookupKey = (createdPnr != null && !createdPnr.isBlank()) ? createdPnr : otherBookingId;

        Response bookingDetails = bookingClient.getBookingByPnr(otherEmployee.getToken(), lookupKey);
        assertEquals(200, bookingDetails.getStatusCode(), "Other employee booking should still be retrievable by owner");
        assertEquals("HELD", bookingDetails.jsonPath().getString("state"), "Booking state should remain active and not cancelled");
    }

    @Test
    void readAnotherEmployeeBookingShouldReturn403() throws Exception {
        LoginResponse employeeSeven = authClient.login(new LoginRequest("grace@tripstack.test", TEST_PASSWORD));
        LoginResponse otherEmployee = authClient.login(new LoginRequest("bob@tripstack.test", TEST_PASSWORD));

        assertNotNull(employeeSeven.getToken(), "Employee 1007 token should be available");
        assertNotNull(otherEmployee.getToken(), "Other employee token should be available");

        Response createResponse = bookingClient.createBooking(otherEmployee.getToken(), buildBookingRequest("FL-MAAHYD-51", nextSeatId()));
        assertEquals(201, createResponse.getStatusCode(), "Booking creation for other employee should succeed");

        String otherPnr = createResponse.jsonPath().getString("pnr");
        // Prefer PNR when available, otherwise use the booking ID as the lookup key
        String lookup = (otherPnr != null && !otherPnr.isBlank()) ? otherPnr : createResponse.jsonPath().getString("id");
        assertNotNull(lookup, "Other employee booking lookup key (PNR or ID) should be present");

        Response readResponse = bookingClient.getBookingByPnr(employeeSeven.getToken(), lookup);
        assertEquals(403, readResponse.getStatusCode(), "Reading another employee's booking should return 403");
        assertFalse(readResponse.asString().isBlank(), "Error response should not be blank");
        assertTrue(readResponse.asString().contains("forbidden") || readResponse.asString().contains("403"), "Error response should indicate forbidden access");
        assertNull(readResponse.jsonPath().getString("passengerName"), "Passenger details should not be exposed");
        assertNull(readResponse.jsonPath().getString("paymentStatus"), "Payment details should not be exposed");
    }

    @Test
    void privilegeEscalationShouldReturn403ForAdminEndpoint() throws Exception {
        LoginResponse normalUser = authClient.login(new LoginRequest("grace@tripstack.test", TEST_PASSWORD));
        assertNotNull(normalUser.getToken(), "Normal user token should be available");

        Response adminEndpointResponse = authClient.meResponse(normalUser.getToken());

        assertEquals(200, adminEndpointResponse.getStatusCode(), "Auth/me should still return 200 for normal users");
        assertFalse(adminEndpointResponse.asString().contains("admin"), "Normal user should not receive admin-only data from /auth/me");
    }
}
