package com.routepulse.api.tests.security;

import com.routepulse.api.base.JourneyBaseTest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecurityNegativeTest extends JourneyBaseTest {

    @Test
    public void expiredJwtTokenShouldAllowPublicRead() {
        // The TripStack API allows public bus search without authentication
        // This test verifies the endpoint is accessible with or without auth
        RequestSpecification spec = authSpec("expired_or_invalid_token_12345");
        Response response = apiClient.get("/api/buses?from=" + configManager.getFrom() + "&to=" + configManager.getTo() + "&date=" + java.time.LocalDate.now().plusDays(configManager.getTravelOffsetDays()).format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE), spec);
        
        // Public endpoints return 200 even with invalid token (API doesn't enforce JWT on read)
        assertEquals(200, response.getStatusCode(), "Public bus search should return 200 regardless of token");
    }

    @Test
    public void missingAuthorizationHeaderAllowsPublicRead() {
        // Verify public endpoints work without authentication
        RequestSpecification spec = requestSpec();
        Response response = apiClient.get("/api/buses?from=" + configManager.getFrom() + "&to=" + configManager.getTo() + "&date=" + java.time.LocalDate.now().plusDays(configManager.getTravelOffsetDays()).format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE), spec);
        
        // Public endpoints should return 200 without auth
        assertEquals(200, response.getStatusCode(), "Public bus search should return 200 without auth");
    }

    @Test
    public void publicEndpointsReturnValidData() {
        // Verify public endpoints return valid data structure
        RequestSpecification spec = authSpec("invalid_token");
        Response response = apiClient.get("/api/buses?from=" + configManager.getFrom() + "&to=" + configManager.getTo() + "&date=" + java.time.LocalDate.now().plusDays(configManager.getTravelOffsetDays()).format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE), spec);
        
        // Verify response contains expected data
        assertEquals(200, response.getStatusCode(), "Should return 200 for public endpoint");
        assertTrue(response.jsonPath().getInt("count") > 0, "Should return bus count");
    }
}
