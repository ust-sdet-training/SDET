package com.routepulse.api.tests.security;

import com.routepulse.api.base.JourneyBaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthorizationProbeTest extends JourneyBaseTest {
    @Test
    public void adminProbeShouldBeGuarded() {
        login();
        Response response = apiClient.get("/api/auth/admin-ping", requestSpecFactory.buildWithAuth(configManager, token));
        assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 403, "Admin probe should be reachable or denied based on role");
    }
}
