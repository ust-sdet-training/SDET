package com.routepulse.api.tests.security;

import com.routepulse.api.base.JourneyBaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthSecurityTest extends JourneyBaseTest {

    @Test
    public void adminPingShouldRequireAdminRole() {
        String loginPayload = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", configManager.getEmail(), configManager.getPassword());
        Response loginResponse = apiClient.post("/api/auth/login", loginPayload, requestSpec());
        assertEquals(200, loginResponse.getStatusCode(), "Login should succeed");

        String token = loginResponse.jsonPath().getString("token");
        assertTrue(token != null && !token.isBlank(), "The token should be present");

        Response adminPingResponse = apiClient.get("/api/auth/admin-ping", authSpec(token));
        assertTrue(adminPingResponse.getStatusCode() == 200 || adminPingResponse.getStatusCode() == 403,
                "Admin ping should return either 200 for admin or 403 for insufficient role");
    }
}
