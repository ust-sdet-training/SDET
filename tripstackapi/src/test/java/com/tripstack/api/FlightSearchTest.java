package com.tripstack.api;

import com.tripstack.base.BaseTest;
import com.tripstack.model.request.FlightSearchRequest;
import com.tripstack.services.FlightService;
import com.tripstack.utils.TestDataFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FlightSearchTest extends BaseTest {

    private final FlightService flightService = new FlightService();

    @Test
    @DisplayName("Verify user can search flights")
    void verifyFlightSearch() {

        // Arrange
        FlightSearchRequest request = TestDataFactory.flightSearch();

        // Act
        Response response = flightService.searchFlights(request);

        // Assert
        response.then().statusCode(200);

        assertTrue(response.jsonPath().getInt("count") > 0);

        assertNotNull(response.jsonPath().getString("flights[0].id"));

        assertNotNull(response.jsonPath().getString("flights[0].flight_no"));

        assertTrue(response.jsonPath().getLong("flights[0].total_paise") > 0);

    }

    @Test
    @DisplayName("Verify seat map for selected flight")
    void verifySeatMap() {

        // Arrange
        FlightSearchRequest request = TestDataFactory.flightSearch();

        Response flightResponse = flightService.searchFlights(request);

        String flightId =
                flightResponse.jsonPath().getString("flights[0].id");

        // Act
        Response seatResponse =
                flightService.getSeatMap(flightId);

        // Assert
        seatResponse.then().statusCode(200);

        assertEquals(
                flightId,
                seatResponse.jsonPath().getString("flight_id"));

        assertTrue(
                seatResponse.jsonPath().getInt("available") > 0);

        assertNotNull(
                seatResponse.jsonPath().getString("rows[0].seats[0].seat_id"));

    }

    @Test
    @DisplayName("Verify search without origin")
    void verifySearchWithoutOrigin() {

        FlightSearchRequest request =
                new FlightSearchRequest(
                        "",
                        "GOI",
                        "2026-08-01",
                        1,
                        "economy"
                );

        flightService.searchFlights(request)
                .then()
                .statusCode(400);

    }

    @Test
    @DisplayName("Verify search without destination")
    void verifySearchWithoutDestination() {

        FlightSearchRequest request =
                new FlightSearchRequest(
                        "BOM",
                        "",
                        "2026-08-01",
                        1,
                        "economy"
                );

        flightService.searchFlights(request)
                .then()
                .statusCode(400);

    }

}