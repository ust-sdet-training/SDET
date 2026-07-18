package com.routepulse.api.tests.security;

import com.routepulse.api.base.JourneyBaseTest;
import com.routepulse.api.client.HttpGateway;
import com.routepulse.api.specs.RequestBlueprintFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccessControlProbeTest extends JourneyBaseTest {
    @Test
    public void protectedEndpointShouldRequireAuth() {
        HttpGateway gateway = new HttpGateway();
        RequestBlueprintFactory factory = new RequestBlueprintFactory();
        Response response = gateway.post("/api/bookings", "{}", factory.build(configManager));
        assertTrue(response.getStatusCode() == 401 || response.getStatusCode() == 400, "Protected route should fail without auth");
    }
}
