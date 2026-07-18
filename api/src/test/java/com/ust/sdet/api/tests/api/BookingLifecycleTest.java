package com.ust.sdet.api.tests.api;

import com.ust.sdet.api.base.BaseTest;
import com.ust.sdet.api.models.BookingConfirmResponse;
import com.ust.sdet.api.models.BookingHoldResponse;
import com.ust.sdet.api.models.BookingRetrieveResponse;
import com.ust.sdet.api.models.Bus;
import com.ust.sdet.api.models.BusSearchResponse;
import com.ust.sdet.api.models.BusSeatMap;
import com.ust.sdet.api.models.PaymentResponse;
import com.ust.sdet.api.services.BookingService;
import com.ust.sdet.api.services.BusService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingLifecycleTest extends BaseTest {

    @Test
    public void completeBookingLifecycleShouldWork() {
        login();

        // Stage 1: Search for buses
        String travelDate = LocalDate.now().plusDays(17).format(DateTimeFormatter.ISO_LOCAL_DATE);
        BusService busService = new BusService(apiClient, requestSpecFactory, configManager);

        BusSearchResponse searchResponse = busService.searchBuses("BLR", "HYD", travelDate);
        assertNotNull(searchResponse, "Bus search response should not be null");
        assertTrue(searchResponse.getCount() > 0, "At least one bus should be returned");

        List<Bus> buses = searchResponse.getBuses();
        assertFalse(buses.isEmpty(), "Bus list should not be empty");

        Bus targetBus = buses.stream()
                .filter(bus -> "ac-semi".equalsIgnoreCase(bus.getKind()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected an AC semi bus for the requested route"));

        System.out.println("Selected bus: " + targetBus.getId());

        // Stage 2: Determine seats and hold them
        BusSeatMap seatMap = busService.getSeatMap(targetBus.getId());
        assertNotNull(seatMap, "Seat map should not be null");

        List<String> availableSeats = new java.util.ArrayList<>();
        if (seatMap.getDecks() != null) {
            if (seatMap.getDecks().getUpper() != null) {
                for (var seat : seatMap.getDecks().getUpper()) {
                    if (seat.getSeatId() != null && (seat.getState() == null || seat.getState().equalsIgnoreCase("available") || seat.getState().equalsIgnoreCase("free") || seat.getState().equalsIgnoreCase("open"))) {
                        availableSeats.add(seat.getSeatId());
                        if (availableSeats.size() >= 2) break;
                    }
                }
            }
            if (availableSeats.size() < 2 && seatMap.getDecks().getLower() != null) {
                for (var seat : seatMap.getDecks().getLower()) {
                    if (seat.getSeatId() != null && (seat.getState() == null || seat.getState().equalsIgnoreCase("available") || seat.getState().equalsIgnoreCase("free") || seat.getState().equalsIgnoreCase("open"))) {
                        availableSeats.add(seat.getSeatId());
                        if (availableSeats.size() >= 2) break;
                    }
                }
            }
        }

        assertTrue(availableSeats.size() >= 2, "At least two available seats should be found");
        String[] seatIds = availableSeats.subList(0, 2).toArray(new String[0]);

        BookingService bookingService = new BookingService(apiClient, requestSpecFactory, configManager, token);
        
        BookingHoldResponse holdResponse = bookingService.holdSeats("bus", targetBus.getId(), seatIds);
        assertNotNull(holdResponse, "Hold response should not be null");
        assertEquals("HELD", holdResponse.getStatus(), "Hold status should be HELD");
        assertNotNull(holdResponse.getHoldId(), "Hold ID should be returned");

        System.out.println("Seats held: " + holdResponse.getHoldId());

        // Stage 3: Process payment
        PaymentResponse paymentResponse = bookingService.processPayment(holdResponse.getHoldId());
        assertNotNull(paymentResponse, "Payment response should not be null");
        assertEquals("PAYMENT_PENDING", paymentResponse.getStatus(), "Payment status should be PAYMENT_PENDING");
        assertNotNull(paymentResponse.getTransactionId(), "Transaction ID should be returned");

        System.out.println("Payment processed: " + paymentResponse.getTransactionId());

        // Stage 4: Confirm booking
        BookingConfirmResponse confirmResponse = bookingService.confirmBooking(holdResponse.getHoldId());
        assertNotNull(confirmResponse, "Confirm response should not be null");
        assertEquals("CONFIRMED", confirmResponse.getStatus(), "Booking status should be CONFIRMED");
        assertNotNull(confirmResponse.getPnr(), "PNR should be returned");

        System.out.println("Booking confirmed with PNR: " + confirmResponse.getPnr());

        // Verify PNR is not null before retrieve
        assertNotNull(confirmResponse.getPnr(), "PNR must not be null before retrieve");
        assertFalse(confirmResponse.getPnr().isEmpty(), "PNR must not be empty");

        System.out.println("✓ Booking lifecycle complete - HELD, PAYMENT_PENDING, and CONFIRMED stages passed!");
        
        // TODO: Stage 5 retrieve commented out pending endpoint path verification
        // BookingRetrieveResponse retrieveResponse = bookingService.getBookingByPnr(confirmResponse.getPnr());
        // assertNotNull(retrieveResponse, "Retrieved booking should not be null");
        // assertEquals(confirmResponse.getPnr(), retrieveResponse.getPnr(), "PNR should match");
    }
}
