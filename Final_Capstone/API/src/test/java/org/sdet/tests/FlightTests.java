package org.sdet.tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sdet.API_Framework.builders.FlightSearchBuilder;
import org.sdet.API_Framework.builders.LoginBuilder;
import org.sdet.API_Framework.clients.AuthClient;
import org.sdet.API_Framework.clients.FlightClient;
import org.sdet.API_Framework.model.response.LoginResponse;
import org.sdet.API_Framework.utils.TokenManager;

import static org.junit.jupiter.api.Assertions.*;

public class FlightTests extends BaseTest {

    private final AuthClient authClient = new AuthClient();
    private final FlightClient flightClient = new FlightClient();

    @BeforeEach
    void login() {

        Response loginResponse =
                authClient.login(LoginBuilder.validLogin());

        LoginResponse response =
                loginResponse.as(LoginResponse.class);

        TokenManager.setToken(response.getToken());
    }

    @Test
    @DisplayName("Verify user can search flights successfully")
    void shouldSearchFlightsSuccessfully() {

        Response response =
                flightClient.searchFlights(
                        FlightSearchBuilder.validSearch()
                );

        assertEquals(200, response.getStatusCode());

        JsonPath json =
                response.jsonPath();

        assertEquals("BOM", json.getString("from"));
        assertEquals("MAA", json.getString("to"));

        assertTrue(json.getInt("count") > 0);

        assertNotNull(json.getString("flights[0].id"));
        assertNotNull(json.getString("flights[0].airline_name"));
        assertNotNull(json.getString("flights[0].flight_no"));

        assertTrue(json.getInt("flights[0].total_paise") > 0);
    }

    @Test
    @DisplayName("Verify searched flight has available seats")
    void shouldReturnAvailableSeats() {

        Response flightResponse =
                flightClient.searchFlights(
                        FlightSearchBuilder.validSearch()
                );

        assertEquals(200, flightResponse.getStatusCode());

        JsonPath flightJson =
                flightResponse.jsonPath();

        String flightId =
                flightJson.getString("flights[0].id");

        assertNotNull(flightId);

        Response seatResponse =
                flightClient.getSeatMap(flightId);

        assertEquals(200, seatResponse.getStatusCode());

        JsonPath seatJson =
                seatResponse.jsonPath();

        int totalSeats =
                seatJson.getInt("total");

        int availableSeats =
                seatJson.getInt("available");

        assertTrue(totalSeats > 0);
        assertTrue(availableSeats > 0);
        assertTrue(availableSeats <= totalSeats);
    }
}