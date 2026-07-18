package com.routepulse.api.tests.api;

import com.routepulse.api.base.JourneyBaseTest;
import com.routepulse.api.models.BusSearchResponse;
import com.routepulse.api.services.CoachService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CoachSearchTest extends JourneyBaseTest {
    @Test
    public void busSearchAndSeatMapShouldWork() {
        CoachService coachService = new CoachService(apiClient, requestSpecFactory, configManager);
        BusSearchResponse response = coachService.searchBuses(configManager.getFrom(), configManager.getTo(), LocalDate.now().plusDays(configManager.getTravelOffsetDays()).format(DateTimeFormatter.ISO_LOCAL_DATE));

        assertNotNull(response, "Search response should not be null");
        assertTrue(response.getCount() >= 0, "Search count should be non-negative");
    }
}
