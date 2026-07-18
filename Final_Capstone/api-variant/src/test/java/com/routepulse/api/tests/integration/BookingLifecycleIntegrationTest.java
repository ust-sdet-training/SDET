package com.routepulse.api.tests.integration;

import com.routepulse.api.base.JourneyBaseTest;
import com.routepulse.api.db.assertions.BookingDataAssertions;
import com.routepulse.api.db.queries.BookingQueries;
import com.routepulse.api.models.*;
import com.routepulse.api.services.BookingService;
import com.routepulse.api.services.BusService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test combining API and Database validations
 * Verifies:
 * 1. API correctly creates booking in database
 * 2. Database state transitions match API state
 * 3. Data integrity (amounts, seat IDs, inventory match)
 * 4. Ownership assertions (booking belongs to logged-in employee)
 * 5. Refundability flag is preserved
 */
@DisplayName("Booking Lifecycle - API & Database Integration")
public class BookingLifecycleIntegrationTest extends JourneyBaseTest {

    @BeforeAll
    public static void setUpStatic() {
        BookingQueries.init();  // Initialize database connection once for all tests
    }

    @AfterAll
    public static void tearDownStatic() {
        BookingQueries.close();  // Close database connection after all tests
    }

    @Test
    @DisplayName("Complete booking lifecycle with database integrity verification")
    public void completeBookingLifecycleWithDatabaseVerification() {
        // Stage 1: Authenticate
        login();  // Use inherited login() method from BaseTest
        int expectedEmpId = 1020; 

        System.out.println("\n=== Stage 1: Authentication ===");
        assertNotNull(token, "Token should not be null");
        System.out.println("✓ API: Authenticated with token: " + token.substring(0, 20) + "...");

        // Stage 2: Search buses
        System.out.println("\n=== Stage 2: Bus Search ===");
        BusService busService = new BusService(apiClient, requestSpecFactory, configManager);
        BusSearchResponse searchResponse = busService.searchBuses(configManager.getFrom(), configManager.getTo(), java.time.LocalDate.now().plusDays(configManager.getTravelOffsetDays()).format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE));
        
        assertTrue(searchResponse.getCount() > 0, "At least one bus should be returned");
        Bus targetBus = searchResponse.getBuses().stream()
                .filter(bus -> "ac-semi".equalsIgnoreCase(bus.getKind()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected an AC semi bus"));

        System.out.println("✓ API: Found bus " + targetBus.getId() + " - Rate: " + targetBus.getFarePaise());

        // Stage 3: Determine seats to hold via seat map
        System.out.println("\n=== Stage 3: Determine seats to hold ===");
        BusSeatMap seatMap = busService.getSeatMap(targetBus.getId());
        assertNotNull(seatMap, "Seat map should not be null");
        assertNotNull(seatMap.getDecks(), "Seat map decks should not be null");

        List<String> availableSeats = new ArrayList<>();
        if (seatMap.getDecks().getUpper() != null) {
            for (BusSeatMap.Seat seat : seatMap.getDecks().getUpper()) {
                if (seat.getSeatId() != null && !seat.getSeatId().isBlank()) {
                    String state = seat.getState();
                    if (state == null || state.equalsIgnoreCase("available") || state.equalsIgnoreCase("free") || state.equalsIgnoreCase("open")) {
                        availableSeats.add(seat.getSeatId());
                    }
                }
                if (availableSeats.size() >= 2) {
                    break;
                }
            }
        }
        if (availableSeats.size() < 2 && seatMap.getDecks().getLower() != null) {
            for (BusSeatMap.Seat seat : seatMap.getDecks().getLower()) {
                if (seat.getSeatId() != null && !seat.getSeatId().isBlank()) {
                    String state = seat.getState();
                    if (state == null || state.equalsIgnoreCase("available") || state.equalsIgnoreCase("free") || state.equalsIgnoreCase("open")) {
                        availableSeats.add(seat.getSeatId());
                    }
                }
                if (availableSeats.size() >= 2) {
                    break;
                }
            }
        }

        assertTrue(availableSeats.size() >= 2, "At least two available seats should be found for the selected bus");
        String[] seatIds = availableSeats.subList(0, 2).toArray(new String[0]);
        System.out.println("✓ API: Selected seats " + String.join(", ", seatIds));

        System.out.println("\n=== Stage 4: Hold Seats ===");
        BookingService bookingService = new BookingService(apiClient, requestSpecFactory, configManager, token);
        BookingHoldResponse holdResponse = bookingService.holdSeats("bus", targetBus.getId(), seatIds);
        String bookingId = holdResponse.getHoldId();
        
        assertNotNull(bookingId, "Hold ID should be returned");
        assertEquals("HELD", holdResponse.getStatus(), "Hold status should be HELD");
        System.out.println("✓ API: Seats held - Booking ID: " + bookingId);

        // Stage 4: Verify hold in database
        System.out.println("\n=== Stage 4: Database Verification - Hold State ===");
        BookingDataAssertions.assertBookingHeldInDatabase(bookingId, targetBus.getId(), seatIds, expectedEmpId);
        BookingDataAssertions.assertRefundableFlag(bookingId, true);
        BookingDataAssertions.assertBookingOwnership(bookingId, expectedEmpId);

        // Stage 5: Process payment via API
        System.out.println("\n=== Stage 5: Process Payment ===");
        PaymentResponse paymentResponse = bookingService.processPayment(bookingId);
        
        assertNotNull(paymentResponse, "Payment response should not be null");
        assertEquals("PAYMENT_PENDING", paymentResponse.getStatus(), "Status should be PAYMENT_PENDING");
        System.out.println("✓ API: Payment processed - Status: " + paymentResponse.getStatus());

        // Stage 6: Verify payment state in database
        System.out.println("\n=== Stage 6: Database Verification - Payment State ===");
        BookingDataAssertions.assertBookingPaymentPendingInDatabase(bookingId, holdResponse.getAmountPaise());

        // Stage 7: Confirm booking via API
        System.out.println("\n=== Stage 7: Confirm Booking ===");
        BookingConfirmResponse confirmResponse = bookingService.confirmBooking(bookingId);
        
        assertNotNull(confirmResponse, "Confirm response should not be null");
        assertEquals("CONFIRMED", confirmResponse.getStatus(), "Status should be CONFIRMED");
        assertNotNull(confirmResponse.getPnr(), "PNR should be generated");
        System.out.println("✓ API: Booking confirmed - PNR: " + confirmResponse.getPnr());

        // Stage 8: Verify confirmation in database
        System.out.println("\n=== Stage 8: Database Verification - Confirmed State ===");
        BookingDataAssertions.assertBookingConfirmedInDatabase(bookingId, confirmResponse.getPnr());
        BookingDataAssertions.assertBookingRetrievableByPnr(confirmResponse.getPnr());
        BookingDataAssertions.assertSeatIdsMatch(bookingId, seatIds);

        // Stage 9: Verify employee has booking
        System.out.println("\n=== Stage 9: Database Verification - Employee Ownership ===");
        BookingDataAssertions.assertEmployeeHasBooking(expectedEmpId, bookingId);

        // Stage 10: Retrieve via API and verify PNR matches database
        System.out.println("\n=== Stage 10: Retrieve Booking and Final Verification ===");
        BookingRetrieveResponse retrieveResponse = bookingService.getBookingByPnr(confirmResponse.getPnr());
        
        assertNotNull(retrieveResponse, "Retrieved booking should not be null");
        assertEquals(confirmResponse.getPnr(), retrieveResponse.getPnr(), "PNR should match");
        assertEquals("CONFIRMED", retrieveResponse.getStatus(), "Status should still be confirmed");
        
        System.out.println("✓ API: Booking retrieved - PNR: " + retrieveResponse.getPnr() + ", Status: " + retrieveResponse.getStatus());

        // Summary
        System.out.println("\n=== ✓ Test Summary ===");
        System.out.println("Complete booking lifecycle verified:");
        System.out.println("  • API: HELD → PAYMENT_PENDING → CONFIRMED");
        System.out.println("  • DB: State transitions verified");
        System.out.println("  • DB: Ownership verified (Employee " + expectedEmpId + ")");
        System.out.println("  • DB: PNR generated and retrievable");
        System.out.println("  • DB: Data integrity validated");
    }
}
