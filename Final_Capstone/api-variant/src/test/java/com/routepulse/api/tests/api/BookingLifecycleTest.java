package com.routepulse.api.tests.api;

import com.routepulse.api.base.JourneyBaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingLifecycleTest extends JourneyBaseTest {

    @Test
    public void bookingLifecycleShouldFlowThroughHoldAndConfirmEndpoints() {
        String loginPayload = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", configManager.getEmail(), configManager.getPassword());
        Response loginResponse = apiClient.post("/api/auth/login", loginPayload, requestSpec());
        assertEquals(200, loginResponse.getStatusCode(), "Login should succeed");

        String token = loginResponse.jsonPath().getString("token");
        assertTrue(token != null && !token.isBlank(), "Token should be returned");

        Response resetResponse = apiClient.post("/api/reset", null, authSpec(token));
        assertEquals(200, resetResponse.getStatusCode(), "Reset should succeed for a clean namespace");

        String travelDate = LocalDate.now()
                .plusDays(configManager.getTravelOffsetDays())
                .format(DateTimeFormatter.ISO_LOCAL_DATE);

        Response busSearchResponse = apiClient.get(
                "/api/buses?from=" + configManager.getFrom() + "&to=" + configManager.getTo() + "&date=" + travelDate,
                requestSpec()
        );
        assertEquals(200, busSearchResponse.getStatusCode(), "Bus search should return 200");

        List<Map<String, Object>> buses = busSearchResponse.jsonPath().getList("buses");
        assertTrue(!buses.isEmpty(), "There should be at least one bus for the route");

        String inventoryId = busSearchResponse.jsonPath().getString("buses[0].id");
        assertNotNull(inventoryId, "The first bus should have an inventory id");

        String holdBody = String.format("{\"journeyType\":\"bus\",\"inventoryId\":\"%s\",\"seatIds\":[\"L1\"]}", inventoryId);
        Response holdResponse = apiClient.post("/api/bookings", holdBody, authSpec(token));
        assertTrue(holdResponse.getStatusCode() == 201 || holdResponse.getStatusCode() == 200,
                "Hold should return either 200 or 201");

        String bookingId = holdResponse.jsonPath().getString("id");
        assertNotNull(bookingId, "Hold response should include a booking id");

        Response payResponse = apiClient.post("/api/bookings/" + bookingId + "/pay", "{}", authSpec(token));
        assertTrue(payResponse.getStatusCode() == 200 || payResponse.getStatusCode() == 402 || payResponse.getStatusCode() == 502 || payResponse.getStatusCode() == 504 || payResponse.getStatusCode() == 503,
                "Payment should return a handled lifecycle response");

        Response bookingsResponse = apiClient.get("/api/bookings", authSpec(token));
        assertEquals(200, bookingsResponse.getStatusCode(), "Listing bookings should return 200");
    }
}