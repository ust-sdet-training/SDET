package com.tripstack.tests.flights;

import com.tripstack.base.BaseTest;
import com.tripstack.client.FlightClient;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.greaterThan;

public class SearchFlightsTest extends BaseTest {

    FlightClient flightClient =
            new FlightClient();

    @Test
    void shouldSearchFlights() {

        flightClient
                .searchFlights(
                        "DEL",
                        "BLR",
                        "2026-08-01"
                )
                .then()
                .statusCode(200)
                .body(
                        "count",
                        greaterThan(0)
                );
    }
}