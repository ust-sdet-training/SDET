package com.week7.finalgate.tests;

import com.week7.finalgate.API.Factory.RestClient;
import com.week7.finalgate.API.models.Flight;
import com.week7.finalgate.API.service.FlightService;
import com.week7.finalgate.API.support.ApiContext;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FlightSearchTest {

    @BeforeAll
    static void setup() {
        RestClient.initialize();
    }

    @Test
    void shouldSearchFlights() {

        ApiContext context =
                new ApiContext();

        FlightService service =
                new FlightService(context);

        Flight flight =
                service.searchFlight(
                        "MAA",
                        "BLR",
                        LocalDate.now()
                                .plusDays(6)
                                .toString()
                );

        assertNotNull(flight);

        assertEquals("MAA", flight.getOrigin());

        assertEquals("BLR", flight.getDest());

        assertNotNull(context.getFlightId());

        System.out.println(
                "Flight ID : " +
                        context.getFlightId()
        );

    }

}