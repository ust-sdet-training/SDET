package com.ust.sdet.api.tests.api;

import com.ust.sdet.api.base.BaseTest;
import com.ust.sdet.api.models.Flight;
import com.ust.sdet.api.models.FlightSearchResponse;
import com.ust.sdet.api.models.FlightSeatMap;
import com.ust.sdet.api.services.FlightService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlightSearchTest extends BaseTest {

    @Test
    public void flightSearchAndSeatMapShouldWorkForPuneToMumbai() {
        login();

        // E13 Justin: one-way flight LKO → DEL, date offset +20 days, economy class
        String travelDate = LocalDate.now().plusDays(20).format(DateTimeFormatter.ISO_LOCAL_DATE);
        FlightService flightService = new FlightService(apiClient, requestSpecFactory, configManager);

        FlightSearchResponse searchResponse = flightService.searchFlights("LKO", "DEL", travelDate);
        assertNotNull(searchResponse, "Flight search response should not be null");
        assertTrue(searchResponse.getCount() > 0, "At least one flight should be returned for the requested route");

        List<Flight> flights = searchResponse.getFlights();
        assertFalse(flights.isEmpty(), "Flight list should not be empty");

        Flight targetFlight = flights.stream()
                .filter(flight -> "FL-LKODEL-51".equalsIgnoreCase(flight.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected the requested flight in the search results"));

        FlightSeatMap seatMap = flightService.getSeatMap(targetFlight.getId());
        assertNotNull(seatMap, "Seat map should not be null");
        assertNotNull(seatMap.getRows(), "Seat map should contain rows");
        assertFalse(seatMap.getRows().isEmpty(), "Seat map should contain at least one row");
    }
}
