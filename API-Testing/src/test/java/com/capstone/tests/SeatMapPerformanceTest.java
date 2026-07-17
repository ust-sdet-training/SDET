package com.capstone.tests;

import com.capstone.api.FlightApiClient;
import com.capstone.config.AppConfig;
import com.capstone.support.BaseApiTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SeatMapPerformanceTest extends BaseApiTest {

    private FlightApiClient flightApiClient;

    @BeforeEach
    void initClient() {
        flightApiClient = new FlightApiClient(request);
    }

    @Test
    @Tag("performance")
    void shouldLoadFlightSeatMapWithinConfiguredResponseTime() {
        String flightId = flightApiClient.selectFirstFlightId(
                "CCU", "BOM", "2026-08-10", 1, "economy");

        Response seatMapResponse = flightApiClient.getSeatMap(flightId);
        long responseTimeMs = seatMapResponse.timeIn(TimeUnit.MILLISECONDS);
        int responseTimeTargetMs = AppConfig.getInt("seat.map.max.response.time.ms");

        assertEquals(200, seatMapResponse.statusCode());
        assertTrue(responseTimeMs <= responseTimeTargetMs,
                () -> "Seat map for " + flightId + " took " + responseTimeMs
                        + " ms; target is " + responseTimeTargetMs + " ms");
        System.out.println(responseTimeMs);
        System.out.println(responseTimeTargetMs);
    }
}
