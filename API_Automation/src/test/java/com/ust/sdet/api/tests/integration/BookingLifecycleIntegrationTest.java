package com.ust.sdet.api.tests.integration;

import com.ust.sdet.api.base.BaseTest;
import com.ust.sdet.api.db.assertions.BookingDataAssertions;
import com.ust.sdet.api.db.queries.BookingQueries;
import com.ust.sdet.api.models.*;
import com.ust.sdet.api.services.BookingService;
import com.ust.sdet.api.services.FlightService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test combining API and Database validations for flight bookings
 * Verifies:
 * 1. API correctly creates booking in database
 * 2. Database state transitions match API state
 * 3. Data integrity (amounts, seat IDs, inventory match)
 * 4. Ownership assertions (booking belongs to logged-in employee)
 * 5. PNR generation and retrieval
 * 
 * Test Profile: E05 Bhumika (emp_id: 1005)
 * Journey: Flight round-trip PUN → BOM, +7 days, cabin class
 */
@DisplayName("Booking Lifecycle - Flight API & Database Integration")
public class BookingLifecycleIntegrationTest extends BaseTest {

    @BeforeAll
    public static void setUpStatic() {
        BookingQueries.init();  // Initialize database connection once for all tests
    }

    @AfterAll
    public static void tearDownStatic() {
        BookingQueries.close();  // Close database connection after all tests
    }

    @Test
    @DisplayName("Complete flight booking lifecycle with database integrity verification")
    public void completeFlightBookingLifecycleWithDatabaseVerification() {
        // Stage 1: Authenticate
        login();  // Use inherited login() method from BaseTest
        int expectedEmpId = 1005;  // E05 Bhumika employee ID

        System.out.println("\n=== Stage 1: Authentication ===");
        assertNotNull(token, "Token should not be null");
        System.out.println("✓ API: Authenticated with token: " + token.substring(0, 20) + "...");

        // Stage 2: Search flights
        System.out.println("\n=== Stage 2: Flight Search ===");
        String travelDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ISO_LOCAL_DATE);
        FlightService flightService = new FlightService(apiClient, requestSpecFactory, configManager);
        
        FlightSearchResponse searchResponse = flightService.searchFlights("PUN", "BOM", travelDate);
        
        assertTrue(searchResponse.getCount() > 0, "At least one flight should be returned");
        List<Flight> flights = searchResponse.getFlights();
        
        Flight targetFlight = flights.stream()
                .filter(flight -> flight.getId() != null)
                .findFirst()
                .orElseThrow(() -> new AssertionError("No flights available for PUN → BOM route"));

        System.out.println("✓ API: Found flight " + targetFlight.getId() + " - Airline: " + targetFlight.getAirlineName());

        // Stage 3: Retrieve seat map
        System.out.println("\n=== Stage 3: Retrieve Seat Map ===");
        FlightSeatMap seatMap = flightService.getSeatMap(targetFlight.getId());
        assertNotNull(seatMap, "Seat map should not be null");
        assertNotNull(seatMap.getRows(), "Seat map rows should not be null");
        assertTrue(!seatMap.getRows().isEmpty(), "Seat map should contain rows");

        // Stage 4: Select available seats
        System.out.println("\n=== Stage 4: Select Available Seats ===");
        String[] seatIds = new String[2];
        int index = 0;
        for (FlightSeatMap.CabinRow row : seatMap.getRows()) {
            for (FlightSeatMap.CabinSeat seat : row.getSeats()) {
                if (Boolean.FALSE.equals(seat.getOccupied())) {
                    seatIds[index++] = seat.getSeatId();
                    if (index == 2) {
                        break;
                    }
                }
            }
            if (index == 2) {
                break;
            }
        }
        
        assertTrue(seatIds[0] != null && seatIds[1] != null, "Two seats should be available");
        System.out.println("✓ API: Selected seats " + String.join(", ", seatIds));

        // Stage 5: Hold Seats via API
        System.out.println("\n=== Stage 5: Hold Seats ===");
        BookingService bookingService = new BookingService(apiClient, requestSpecFactory, configManager, token);
        BookingHoldResponse holdResponse = bookingService.holdSeats("flight", targetFlight.getId(), seatIds);
        String bookingId = holdResponse.getHoldId();
        
        assertNotNull(bookingId, "Hold ID should be returned");
        assertEquals("HELD", holdResponse.getStatus(), "Hold status should be HELD");
        System.out.println("✓ API: Seats held - Booking ID: " + bookingId);

        // Stage 6: Verify hold in database
        System.out.println("\n=== Stage 6: Database Verification - Hold State ===");
        BookingDataAssertions.assertBookingHeldInDatabase(bookingId, targetFlight.getId(), seatIds, expectedEmpId);
        BookingDataAssertions.assertRefundableFlag(bookingId, true);
        BookingDataAssertions.assertBookingOwnership(bookingId, expectedEmpId);

        // Stage 7: Process payment via API
        System.out.println("\n=== Stage 7: Process Payment ===");
        PaymentResponse paymentResponse = bookingService.processPayment(bookingId);
        
        assertNotNull(paymentResponse, "Payment response should not be null");
        assertEquals("PAYMENT_PENDING", paymentResponse.getStatus(), "Status should be PAYMENT_PENDING");
        System.out.println("✓ API: Payment processed - Status: " + paymentResponse.getStatus());

        // Stage 8: Verify payment state in database
        System.out.println("\n=== Stage 8: Database Verification - Payment State ===");
        BookingDataAssertions.assertBookingPaymentPendingInDatabase(bookingId, holdResponse.getAmountPaise());

        // Stage 9: Confirm booking via API
        System.out.println("\n=== Stage 9: Confirm Booking ===");
        BookingConfirmResponse confirmResponse = bookingService.confirmBooking(bookingId);
        
        assertNotNull(confirmResponse, "Confirm response should not be null");
        assertEquals("CONFIRMED", confirmResponse.getStatus(), "Status should be CONFIRMED");
        assertNotNull(confirmResponse.getPnr(), "PNR should be generated");
        
        // Verify PNR format: TS-<empId>-<seq>
        String expectedPnrPattern = "TS-" + expectedEmpId + "-";
        assertTrue(confirmResponse.getPnr().startsWith(expectedPnrPattern), 
                "PNR should follow format TS-<empId>-<seq>, got: " + confirmResponse.getPnr());
        
        System.out.println("✓ API: Booking confirmed - PNR: " + confirmResponse.getPnr());

        // Stage 10: Verify confirmation in database
        System.out.println("\n=== Stage 10: Database Verification - Confirmed State ===");
        BookingDataAssertions.assertBookingConfirmedInDatabase(bookingId, confirmResponse.getPnr());
        BookingDataAssertions.assertBookingRetrievableByPnr(confirmResponse.getPnr());
        BookingDataAssertions.assertSeatIdsMatch(bookingId, seatIds);

        // Stage 11: Verify employee has booking
        System.out.println("\n=== Stage 11: Database Verification - Employee Ownership ===");
        BookingDataAssertions.assertEmployeeHasBooking(expectedEmpId, bookingId);

        // Stage 12: Retrieve via API and verify PNR matches database
        System.out.println("\n=== Stage 12: Retrieve Booking and Final Verification ===");
        BookingRetrieveResponse retrieveResponse = bookingService.getBookingByPnr(confirmResponse.getPnr());
        
        assertNotNull(retrieveResponse, "Retrieved booking should not be null");
        assertEquals(confirmResponse.getPnr(), retrieveResponse.getPnr(), "PNR should match");
        assertEquals("CONFIRMED", retrieveResponse.getStatus(), "Status should still be confirmed");
        
        System.out.println("✓ API: Booking retrieved - PNR: " + retrieveResponse.getPnr() + ", Status: " + retrieveResponse.getStatus());

        // Summary
        System.out.println("\n=== ✓ Test Summary ===");
        System.out.println("Complete flight booking lifecycle verified:");
        System.out.println("  • API: HELD → PAYMENT_PENDING → CONFIRMED");
        System.out.println("  • DB: State transitions verified");
        System.out.println("  • DB: Ownership verified (Employee " + expectedEmpId + ")");
        System.out.println("  • DB: PNR generated (" + confirmResponse.getPnr() + ") and retrievable");
        System.out.println("  • DB: Data integrity validated");
        System.out.println("  • DB: Seat IDs: " + String.join(", ", seatIds));
    }
}
