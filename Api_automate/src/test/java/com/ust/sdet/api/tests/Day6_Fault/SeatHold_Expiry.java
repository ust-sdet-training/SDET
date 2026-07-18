package com.ust.sdet.api.tests.Day6_Fault;

import com.ust.sdet.api.models.*;
import com.ust.sdet.api.services.BookingService;
import com.ust.sdet.api.services.FlightService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.ust.sdet.api.base.BaseTest;

import static org.junit.jupiter.api.Assertions.*;

public class SeatHold_Expiry extends BaseTest{

    @Test
    public void seatholdExpiryForFlight() throws InterruptedException {
        login();

        // E13 Justin: one-way flight LKO → DEL, date offset +20 days, economy class
        String travelDate = LocalDate.now().plusDays(20).format(DateTimeFormatter.ISO_LOCAL_DATE);
        FlightService flightService = new FlightService(apiClient, requestSpecFactory, configManager);
        FlightSearchResponse searchResponse = flightService.searchFlights("LKO", "DEL", travelDate);
        assertNotNull(searchResponse, "Flight search response should not be null");
        assertTrue(searchResponse.getCount() > 0, "At least one flight should be returned");

        List<Flight> flights = searchResponse.getFlights();
        assertFalse(flights.isEmpty(), "Flight list should not be empty");

        Flight targetFlight = flights.stream()
                .filter(flight -> "FL-LKODEL-51".equalsIgnoreCase(flight.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected the requested flight"));

        FlightSeatMap seatMap = flightService.getSeatMap(targetFlight.getId());
        assertNotNull(seatMap, "Seat map should not be null");

        String[] seatIds = new String[2];
        Integer timeout_Ttl = 3;

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
        BookingHoldResponse holdResponse = bookingService.holdSeats("flight", targetFlight.getId(), seatIds, timeout_Ttl);

        assertNotNull(holdResponse);
        assertEquals("HELD", holdResponse.getStatus());
        assertNotNull(holdResponse.getHoldId());

        ErrorResponse errorResponse = bookingService.cannotProcessPayment(holdResponse.getHoldId());

        assertEquals("HOLD_EXPIRED", errorResponse.getError());

        assertEquals("HOLD_EXPIRED", errorResponse.getMessage());


    }
}
