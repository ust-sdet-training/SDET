package org.sdet.tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sdet.API_Framework.builders.FlightSearchBuilder;
import org.sdet.API_Framework.builders.LoginBuilder;
import org.sdet.API_Framework.clients.AuthClient;
import org.sdet.API_Framework.clients.FlightClient;
import org.sdet.API_Framework.model.response.LoginResponse;
import org.sdet.API_Framework.utils.TokenManager;

import static org.junit.jupiter.api.Assertions.*;

public class SearchFlightTest extends BaseTest {

    private final AuthClient authClient = new AuthClient();
    private final FlightClient flightClient = new FlightClient();

    @Test
    @DisplayName("Verify user can search available flights")
    public void shouldSearchFlightsSuccessfully() {

        // Login
        Response loginResponse =
                authClient.login(LoginBuilder.validLogin());

        TokenManager.setToken(
                loginResponse.as(LoginResponse.class).getToken()
        );

        // Search Flights
        Response response =
                flightClient.searchFlights(
                        FlightSearchBuilder.validSearch()
                );

        assertEquals(200, response.getStatusCode());

        JsonPath json = response.jsonPath();

        assertTrue(json.getList("flights").size() > 0);
        assertNotNull(json.getString("flights[0].id"));
        assertNotNull(json.getString("flights[0].airline_name"));
        assertNotNull(json.getString("flights[0].flight_no"));

    }

}