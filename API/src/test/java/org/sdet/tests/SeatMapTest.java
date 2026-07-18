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

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SeatMapTest extends BaseTest {

    private final AuthClient authClient = new AuthClient();
    private final FlightClient flightClient = new FlightClient();

    @Test
    @DisplayName("Verify seat map is returned for selected flight")
    void shouldReturnSeatMap() {

        // Login
        Response loginResponse =
                authClient.login(LoginBuilder.validLogin());

        TokenManager.setToken(
                loginResponse.as(LoginResponse.class).getToken()
        );

        // Search Flights
        Response flightResponse =
                flightClient.searchFlights(
                        FlightSearchBuilder.validSearch()
                );

        assertEquals(200, flightResponse.getStatusCode());

        JsonPath flightJson = flightResponse.jsonPath();

        String flightId =
                flightJson.getString("flights[0].id");

        assertNotNull(flightId);

        // Seat Map
        Response seatResponse =
                flightClient.getSeatMap(flightId);

        assertEquals(200, seatResponse.getStatusCode());

        JsonPath seatJson = seatResponse.jsonPath();

        assertNotNull(seatJson.getString("flight_id"));

        assertTrue(seatJson.getInt("total") > 0);

        assertTrue(seatJson.getInt("available") > 0);

        List<Map<String, Object>> rows =
                seatJson.getList("rows");

        assertNotNull(rows);

        assertFalse(rows.isEmpty());

        boolean foundAvailableSeat = false;

        outer:
        for (Map<String, Object> row : rows) {

            List<Map<String, Object>> seats =
                    (List<Map<String, Object>>) row.get("seats");

            if (seats == null) {
                continue;
            }

            for (Map<String, Object> seat : seats) {

                Boolean occupied =
                        (Boolean) seat.get("occupied");

                if (Boolean.FALSE.equals(occupied)) {
                    foundAvailableSeat = true;
                    break outer;
                }
            }
        }

        assertTrue(foundAvailableSeat,
                "No available seats found");

    }
}