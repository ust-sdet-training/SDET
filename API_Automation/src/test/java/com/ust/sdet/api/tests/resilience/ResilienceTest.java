package com.ust.sdet.api.tests.resilience;

import com.ust.sdet.api.base.BaseTest;
import com.ust.sdet.api.models.FlightSearchResponse;
import com.ust.sdet.api.models.FlightSeatMap;
import com.ust.sdet.api.services.FlightService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Resilience test suite for flight booking API
 * Validates:
 * 1. Response times within acceptable limits
 * 2. Data availability and consistency under normal load
 * 3. API stability for critical operations
 * 
 * Test Profile: E05 Bhumika (emp_id: 1005)
 * Route: PUN → BOM flights
 */
@DisplayName("Resilience - API Response Time & Stability")
public class ResilienceTest extends BaseTest {

    @Test
    @DisplayName("Flight search should return data within 10 seconds timeout")
    public void flightSearchShouldReturnDataWithinTimeout() {
        login();

        String travelDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ISO_LOCAL_DATE);
        FlightService flightService = new FlightService(apiClient, requestSpecFactory, configManager);

        long startTime = System.currentTimeMillis();
        
        FlightSearchResponse searchResponse = flightService.searchFlights("PUN", "BOM", travelDate);
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        // Assert response received within reasonable timeout (10 seconds)
        assertTrue(duration < 10000, "Flight search should complete within 10 seconds. Took: " + duration + "ms");
        System.out.println("✓ Flight search completed in " + duration + "ms");
        
        // Verify data integrity
        assertNotNull(searchResponse, "Search response should not be null");
        assertTrue(searchResponse.getCount() > 0, "Should return at least one flight");
        System.out.println("✓ Found " + searchResponse.getCount() + " flights");
    }

    @Test
    @DisplayName("Seat map retrieval should return data within 5 seconds timeout")
    public void seatMapShouldReturnDataWithinTimeout() {
        login();

        String travelDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ISO_LOCAL_DATE);
        FlightService flightService = new FlightService(apiClient, requestSpecFactory, configManager);

        FlightSearchResponse searchResponse = flightService.searchFlights("PUN", "BOM", travelDate);
        assertTrue(searchResponse.getCount() > 0, "At least one flight should be returned");
        
        var flights = searchResponse.getFlights();
        assertTrue(!flights.isEmpty(), "Flight list should not be empty");
        
        var targetFlight = flights.get(0);
        
        long startTime = System.currentTimeMillis();
        
        FlightSeatMap seatMap = flightService.getSeatMap(targetFlight.getId());
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        // Assert response received within timeout (5 seconds)
        assertTrue(duration < 5000, "Seat map retrieval should complete within 5 seconds. Took: " + duration + "ms");
        System.out.println("✓ Seat map retrieved in " + duration + "ms");
        
        // Verify data integrity
        assertNotNull(seatMap, "Seat map should not be null");
        assertNotNull(seatMap.getRows(), "Seat map rows should not be null");
        assertTrue(!seatMap.getRows().isEmpty(), "Seat map should contain rows");
        System.out.println("✓ Seat map contains " + seatMap.getRows().size() + " rows");
    }

    @Test
    @DisplayName("Multiple sequential requests should maintain consistent performance")
    public void multipleSequentialRequestsShouldMaintainPerformance() {
        login();

        String travelDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ISO_LOCAL_DATE);
        FlightService flightService = new FlightService(apiClient, requestSpecFactory, configManager);

        long totalDuration = 0;
        int requestCount = 3;
        long maxDuration = 0;
        long minDuration = Long.MAX_VALUE;

        for (int i = 0; i < requestCount; i++) {
            long startTime = System.currentTimeMillis();
            
            FlightSearchResponse response = flightService.searchFlights("PUN", "BOM", travelDate);
            
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            
            totalDuration += duration;
            maxDuration = Math.max(maxDuration, duration);
            minDuration = Math.min(minDuration, duration);
            
            assertNotNull(response, "Response " + (i + 1) + " should not be null");
            assertTrue(response.getCount() > 0, "Response " + (i + 1) + " should contain flights");
        }

        long avgDuration = totalDuration / requestCount;
        
        // All requests should complete within timeout
        assertTrue(maxDuration < 10000, "All requests should complete within 10 seconds. Max: " + maxDuration + "ms");
        System.out.println("✓ Multiple sequential requests completed:");
        System.out.println("  - Average: " + avgDuration + "ms");
        System.out.println("  - Min: " + minDuration + "ms");
        System.out.println("  - Max: " + maxDuration + "ms");
        
        // Performance should be consistent (max not significantly higher than average)
        double variance = ((double) (maxDuration - avgDuration) / avgDuration) * 100;
        assertTrue(variance < 50, "Performance variance should be < 50%. Actual: " + String.format("%.2f", variance) + "%");
    }
}
