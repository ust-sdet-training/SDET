package com.ust.sdet.api.tests.resilience;

import com.ust.sdet.api.base.BaseTest;
import com.ust.sdet.api.services.BusService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResilienceTest extends BaseTest {

    @Test
    public void busSearchShouldReturnDataWithinTimeout() {
        login();

        String travelDate = LocalDate.now().plusDays(17).format(DateTimeFormatter.ISO_LOCAL_DATE);
        BusService busService = new BusService(apiClient, requestSpecFactory, configManager);

        long startTime = System.currentTimeMillis();
        
        var searchResponse = busService.searchBuses("BLR", "HYD", travelDate);
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        // Assert response received within reasonable timeout (10 seconds)
        assertTrue(duration < 10000, "Bus search should complete within 10 seconds. Took: " + duration + "ms");
        
        // Verify data integrity
        assertNotNull(searchResponse, "Search response should not be null");
        assertTrue(searchResponse.getCount() > 0, "Should return at least one bus");
    }

    @Test
    public void seatMapShouldReturnDataWithinTimeout() {
        login();

        String travelDate = LocalDate.now().plusDays(17).format(DateTimeFormatter.ISO_LOCAL_DATE);
        BusService busService = new BusService(apiClient, requestSpecFactory, configManager);

        var searchResponse = busService.searchBuses("BLR", "HYD", travelDate);
        var buses = searchResponse.getBuses();
        
        assertTrue(!buses.isEmpty(), "At least one bus should exist");
        
        var targetBus = buses.get(0);
        
        long startTime = System.currentTimeMillis();
        
        var seatMap = busService.getSeatMap(targetBus.getId());
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        // Assert seat map retrieved within timeout
        assertTrue(duration < 20000, "Seat map retrieval should complete within 20 seconds. Took: " + duration + "ms");
        
        // Verify data integrity
        assertNotNull(seatMap, "Seat map should not be null");
        assertNotNull(seatMap.getDecks(), "Seat map should have decks");
    }

    @Test
    public void multipleConsecutiveRequestsShouldSucceed() {
        login();

        String travelDate = LocalDate.now().plusDays(17).format(DateTimeFormatter.ISO_LOCAL_DATE);
        BusService busService = new BusService(apiClient, requestSpecFactory, configManager);

        // Make multiple consecutive requests to test reliability
        for (int i = 0; i < 3; i++) {
            var searchResponse = busService.searchBuses("BLR", "HYD", travelDate);
            assertNotNull(searchResponse, "Search response " + (i + 1) + " should not be null");
            assertTrue(searchResponse.getCount() > 0, "Search response " + (i + 1) + " should return buses");
        }
    }
}
