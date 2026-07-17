package com.ust.sdet.api.tests.api;

import com.ust.sdet.api.base.BaseTest;
import com.ust.sdet.api.models.BookingConfirmResponse;
import com.ust.sdet.api.models.BookingHoldResponse;
import com.ust.sdet.api.models.Flight;
import com.ust.sdet.api.models.FlightSearchResponse;
import com.ust.sdet.api.models.FlightSeatMap;
import com.ust.sdet.api.models.PaymentResponse;
import com.ust.sdet.api.services.BookingService;
import com.ust.sdet.api.services.FlightService;
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
    public void completeBookingLifecycleShouldWorkForFlight() {
        login();
        String travelDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ISO_LOCAL_DATE);
        FlightService flightService = new FlightService(apiClient, requestSpecFactory, configManager);
        FlightSearchResponse searchResponse = flightService.searchFlights("PUN", "BOM", travelDate);
        assertNotNull(searchResponse, "Flight search response should not be null");
        assertTrue(searchResponse.getCount() > 0, "At least one flight should be returned");

        List<Flight> flights = searchResponse.getFlights();
        assertFalse(flights.isEmpty(), "Flight list should not be empty");

        Flight targetFlight = flights.stream()
                .filter(flight -> "FL-PUNBOM-51".equalsIgnoreCase(flight.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected the requested flight"));

        FlightSeatMap seatMap = flightService.getSeatMap(targetFlight.getId());
        assertNotNull(seatMap, "Seat map should not be null");

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

        assertEquals(2, seatIds.length, "Two seat IDs should be selected");
        assertNotNull(seatIds[0], "First seat should be available");
        assertNotNull(seatIds[1], "Second seat should be available");

        BookingService bookingService = new BookingService(apiClient, requestSpecFactory, configManager, token);
        BookingHoldResponse holdResponse = bookingService.holdSeats("flight", targetFlight.getId(), seatIds);
        assertNotNull(holdResponse, "Hold response should not be null");
        assertEquals("HELD", holdResponse.getStatus(), "Hold status should be HELD");
        assertNotNull(holdResponse.getHoldId(), "Hold ID should be returned");

        PaymentResponse paymentResponse = bookingService.processPayment(holdResponse.getHoldId());
        assertNotNull(paymentResponse, "Payment response should not be null");
        assertEquals("PAYMENT_PENDING", paymentResponse.getStatus(), "Payment status should be PAYMENT_PENDING");

        BookingConfirmResponse confirmResponse = bookingService.confirmBooking(holdResponse.getHoldId());
        assertNotNull(confirmResponse, "Confirm response should not be null");
        assertEquals("CONFIRMED", confirmResponse.getStatus(), "Booking status should be CONFIRMED");
        assertNotNull(confirmResponse.getPnr(), "PNR should be returned");
    }
}
