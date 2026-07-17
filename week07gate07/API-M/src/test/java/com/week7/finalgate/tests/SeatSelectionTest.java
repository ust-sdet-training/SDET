package com.week7.finalgate.tests;

import com.week7.finalgate.API.Factory.RestClient;
import com.week7.finalgate.API.models.Flight;
import com.week7.finalgate.API.models.Seat;
import com.week7.finalgate.API.service.FlightService;
import com.week7.finalgate.API.service.SeatService;
import com.week7.finalgate.API.support.ApiContext;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class SeatSelectionTest {

    @BeforeAll
    static void setup() {
        RestClient.initialize();
    }

    @Test
    void shouldSelectFirstAvailableSeat() {

        ApiContext context = new ApiContext();

        FlightService flightService =
                new FlightService(context);

        Flight flight =
                flightService.searchFlight(
                        "MAA",
                        "BLR",
                        LocalDate.now()
                                .plusDays(6)
                                .toString()
                );

        assertNotNull(flight);

        SeatService seatService =
                new SeatService(context);

        Seat seat =
                seatService.selectFirstAvailableSeat();

        assertNotNull(seat);

        assertFalse(seat.isOccupied());

        assertEquals(
                seat.getSeatId(),
                context.getSelectedSeat()
        );

        System.out.println(
                "Selected Seat : "
                        + seat.getSeatId()
        );

    }

}