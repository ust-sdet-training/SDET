package com.routepulse.api.tests.security;

import com.routepulse.api.base.JourneyBaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecurityNegativeTest extends JourneyBaseTest {

    @Test
    public void invalidTokenShouldStillAllowPublicBusSearch() {
        Response response = apiClient.get(
                "/api/buses?from=" + configManager.getFrom() + "&to=" + configManager.getTo() + "&date=2026-08-06",
                authSpec("bad-token")
        );

        assertEquals(200, response.getStatusCode(), "Public bus search should remain accessible even with a bad token");
        assertTrue(response.jsonPath().getInt("count") > 0, "The response should still contain real route data");
    }
}
