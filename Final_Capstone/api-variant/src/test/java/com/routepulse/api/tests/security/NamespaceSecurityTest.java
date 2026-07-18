package com.routepulse.api.tests.security;

import com.routepulse.api.base.JourneyBaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NamespaceSecurityTest extends JourneyBaseTest {

    @Test
    public void readingAnotherEmployeesPnrShouldBeRejected() {
        String loginPayload = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", configManager.getEmail(), configManager.getPassword());
        Response loginResponse = apiClient.post("/api/auth/login", loginPayload, requestSpec());
        assertEquals(200, loginResponse.getStatusCode(), "Login should succeed");

        String token = loginResponse.jsonPath().getString("token");
        assertTrue(token != null && !token.isBlank(), "Token should be returned");

        Response response = apiClient.get("/api/bookings/TS-1004-0001", authSpec(token));
        assertTrue(response.getStatusCode() == 403 || response.getStatusCode() == 404,
                "Access to another employee's booking should be rejected");
    }
}
