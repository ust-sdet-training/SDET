package com.routepulse.api.tests.security;

import com.routepulse.api.base.JourneyBaseTest;
import com.routepulse.api.client.HttpGateway;
import com.routepulse.api.specs.RequestBlueprintFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecurityProbeTest extends JourneyBaseTest {
    @Test
    public void searchEndpointShouldStayAccessible() {
        HttpGateway gateway = new HttpGateway();
        RequestBlueprintFactory factory = new RequestBlueprintFactory();
        Response response = gateway.get("/api/buses?from=" + configManager.getFrom() + "&to=" + configManager.getTo() + "&date=" + java.time.LocalDate.now().plusDays(configManager.getTravelOffsetDays()).format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE), factory.build(configManager));
        assertTrue(response.getStatusCode() == 200, "Public search should remain accessible");
    }
}
