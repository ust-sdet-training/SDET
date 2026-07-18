package com.routepulse.api.tests.api;

import com.routepulse.api.base.JourneyBaseTest;
import com.routepulse.api.models.Bus;
import com.routepulse.api.models.BusSearchResponse;
import com.routepulse.api.models.BusSeatMap;
import com.routepulse.api.services.BusService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BusSearchTest extends JourneyBaseTest {

    @Test
    public void busSearchAndSeatMapShouldWork() {
        login();

        String travelDate = LocalDate.now().plusDays(configManager.getTravelOffsetDays()).format(DateTimeFormatter.ISO_LOCAL_DATE);
        BusService busService = new BusService(apiClient, requestSpecFactory, configManager);

        BusSearchResponse searchResponse = busService.searchBuses(configManager.getFrom(), configManager.getTo(), travelDate);
        assertNotNull(searchResponse, "Bus search response should not be null");
        assertTrue(searchResponse.getCount() > 0, "At least one bus should be returned for the requested route");

        List<Bus> buses = searchResponse.getBuses();
        assertFalse(buses.isEmpty(), "Bus list should not be empty");

        Bus targetBus = buses.stream()
                .filter(bus -> "ac-semi".equalsIgnoreCase(bus.getKind()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected an AC semi bus for the requested route"));

        BusSeatMap seatMap = busService.getSeatMap(targetBus.getId());
        assertNotNull(seatMap, "Seat map should not be null");
        assertEquals(targetBus.getId(), seatMap.getBusId(), "Seat map should match the selected bus ID");
        assertNotNull(seatMap.getDecks(), "Seat map should contain decks");
        assertNotNull(seatMap.getDecks().getLower(), "Lower deck seats should be present");
        assertNotNull(seatMap.getDecks().getUpper(), "Upper deck seats should be present");
    }
}
