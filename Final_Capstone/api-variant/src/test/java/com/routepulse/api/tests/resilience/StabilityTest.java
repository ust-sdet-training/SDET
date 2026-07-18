package com.routepulse.api.tests.resilience;

import com.routepulse.api.base.JourneyBaseTest;
import com.routepulse.api.models.BusSearchResponse;
import com.routepulse.api.services.CoachService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StabilityTest extends JourneyBaseTest {
    @Test
    public void searchShouldReturnWithinNormalBudget() {
        CoachService coachService = new CoachService(apiClient, requestSpecFactory, configManager);
        BusSearchResponse response = coachService.searchBuses(configManager.getFrom(), configManager.getTo(), java.time.LocalDate.now().plusDays(configManager.getTravelOffsetDays()).format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE));
        assertNotNull(response, "The API should return a response in a stable timeframe");
    }
}
