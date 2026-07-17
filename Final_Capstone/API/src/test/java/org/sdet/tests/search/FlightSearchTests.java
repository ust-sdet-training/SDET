package org.sdet.tests.search;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.sdet.base.BaseTest;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class FlightSearchTests extends BaseTest {

    @Test
    public void verifyFlightSearch() {

        Response response = searchClient.searchFlights(
                "Hyderabad",
                "Bangalore",
                "2026-07-25"
        );

        response.then()
                .statusCode(200)
                .body("flights", notNullValue());

        assertFalse(
                response.jsonPath().getList("flights").isEmpty(),
                "No flights found"
        );

        String flightId = response.jsonPath()
                .getString("flights[0].id");

        assertNotNull(flightId);
    }

    @Test
    public void verifyInvalidRoute() {

        Response response = searchClient.searchFlights(
                "ABC",
                "XYZ",
                "2026-07-25"
        );

        response.then()
                .statusCode(404);
    }

    @Test
    public void verifyPastDateSearch() {

        Response response = searchClient.searchFlights(
                "Hyderabad",
                "Bangalore",
                "2024-01-01"
        );

        response.then()
                .statusCode(anyOf(is(400), is(422)));
    }
}