package com.routepulse.api.tests.api;

import com.routepulse.api.base.JourneyBaseTest;
import com.routepulse.api.models.BookingConfirmResponse;
import com.routepulse.api.models.BookingHoldResponse;
import com.routepulse.api.models.BookingRetrieveResponse;
import com.routepulse.api.models.Bus;
import com.routepulse.api.models.BusSearchResponse;
import com.routepulse.api.models.BusSeatMap;
import com.routepulse.api.services.CoachService;
import com.routepulse.api.services.ReservationService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationJourneyTest extends JourneyBaseTest {
    @Test
    public void completeBookingLifecycleShouldWork() {
        login();
        resetNamespace();

        CoachService coachService = new CoachService(apiClient, requestSpecFactory, configManager);
        BusSearchResponse searchResponse = coachService.searchBuses(configManager.getFrom(), configManager.getTo(), LocalDate.now().plusDays(configManager.getTravelOffsetDays()).format(DateTimeFormatter.ISO_LOCAL_DATE));
        assertTrue(searchResponse.getCount() > 0, "At least one bus should be returned");

        Bus targetBus = searchResponse.getBuses().get(0);
        BusSeatMap seatMap = coachService.getSeatMap(targetBus.getId());
        assertNotNull(seatMap, "Seat map should be present");

        ReservationService reservationService = new ReservationService(apiClient, requestSpecFactory, configManager, token);
        BookingHoldResponse holdResponse = reservationService.holdSeats("bus", targetBus.getId(), new String[]{"L2"});
        assertNotNull(holdResponse.getHoldId(), "Booking should be created");

        BookingConfirmResponse payResponse = reservationService.payForBooking(holdResponse.getHoldId());
        assertNotNull(payResponse.getStatus(), "Payment response should contain state");

        BookingConfirmResponse confirmResponse = reservationService.confirmBooking(holdResponse.getHoldId());
        assertNotNull(confirmResponse.getPnr(), "Confirmed booking should have a PNR");

        BookingRetrieveResponse retrieved = reservationService.retrieveBooking(confirmResponse.getPnr());
        assertNotNull(retrieved.getPnr(), "Retrieved booking should include PNR");
    }
}
