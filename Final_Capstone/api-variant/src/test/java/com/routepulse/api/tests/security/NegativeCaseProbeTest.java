package com.routepulse.api.tests.security;

import com.routepulse.api.base.JourneyBaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NegativeCaseProbeTest extends JourneyBaseTest {
    @Test
    public void invalidTokenShouldBeHandled() {
        Response response = apiClient.get("/api/auth/me", requestSpecFactory.buildWithAuth(configManager, "bad-token"));
        assertTrue(response.getStatusCode() == 401, "Invalid token should be rejected");
    }
}
