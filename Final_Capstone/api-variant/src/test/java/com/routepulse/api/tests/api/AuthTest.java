package com.routepulse.api.tests.api;

import com.routepulse.api.base.JourneyBaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthTest extends JourneyBaseTest {

    @Test
    public void loginShouldReturnTokenAndEmployeeIdentity() {
        String loginPayload = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", configManager.getEmail(), configManager.getPassword());

        Response response = apiClient.post(
                "/api/auth/login",
                loginPayload,
                requestSpec()
        );

        assertEquals(200, response.getStatusCode(), "Login should succeed with the assigned employee credentials");

        String token = response.jsonPath().getString("token");
        assertTrue(token != null && !token.isBlank(), "The login response should include a bearer token");

        String displayName = response.jsonPath().getString("displayName");
        assertTrue(displayName != null && !displayName.isBlank(), "The login response should include the employee display name");
    }
}
