package com.apitesting.tests;

import com.apitesting.client.FlightClient;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class FlightTests extends BaseTest {

    private FlightClient flightClient;

    @BeforeEach
    void init() {
        flightClient = new FlightClient();
    }

    @Test
    void shouldSearchFlights() {
        Response response = flightClient.searchFlights("PUN", "DEL", "2026-08-09", 1, "economy");

        response.then()
                .statusCode(200)
                .body("from", equalTo("PUN"))
                .body("to", equalTo("DEL"))
                .body("class", equalTo("economy"))
                .body("count", greaterThan(0))
                .body("flights", not(empty()));
    }


    @Test
    void shouldGetSeatMap() {
        Response search = flightClient.searchFlights("PUN", "DEL", "2026-08-09", 1, "economy");

        String flightId = search.jsonPath().getString("flights[0].id");
        Response seats = flightClient.getSeatMap(flightId, "economy");
        seats.then()
                .statusCode(200)
                .body("flight_id", equalTo(flightId))
                .body("available", greaterThan(0))
                .body("rows", not(empty()));
    }


    @Test
    void shouldRejectMissingFromAndTo() {
        Response response = flightClient.searchFlights("", "", "2026-08-09", 1, "economy");

        response.then()
                .statusCode(400)
                .body("error", equalTo("from and to are required"));
    }
}