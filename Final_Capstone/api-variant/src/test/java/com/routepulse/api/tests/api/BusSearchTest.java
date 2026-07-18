package com.routepulse.api.tests.api;

import com.routepulse.api.base.JourneyBaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BusSearchTest extends JourneyBaseTest {

    @Test
    public void publicBusSearchShouldReturnRouteDataAndBuses() {
        String tripDate = LocalDate.now()
                .plusDays(configManager.getTravelOffsetDays())
                .format(DateTimeFormatter.ISO_LOCAL_DATE);

        Response response = apiClient.get(
                "/api/buses?from=" + configManager.getFrom() + "&to=" + configManager.getTo() + "&date=" + tripDate,
                requestSpec()
        );

        assertEquals(200, response.getStatusCode(), "The public bus search endpoint should respond with 200");
        assertEquals(configManager.getFrom(), response.jsonPath().getString("from"));
        assertEquals(configManager.getTo(), response.jsonPath().getString("to"));
        assertTrue(response.jsonPath().getInt("count") > 0, "At least one bus should be returned for the route");

        List<Map<String, Object>> buses = response.jsonPath().getList("buses");
        assertFalse(buses.isEmpty(), "The bus list should not be empty");

        String firstBusId = response.jsonPath().getString("buses[0].id");
        assertNotNull(firstBusId, "The first bus should have an id");
        assertTrue(firstBusId.startsWith("BUS-"), "The bus id should look like a real route id");
    }
}
