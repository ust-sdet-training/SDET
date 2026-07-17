package com.tripstack.tests.buses;

import com.tripstack.base.BaseTest;
import com.tripstack.config.ConfigManager;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@Tag("api")
@Tag("bus")
public class BusSeatMapTest extends BaseTest {

    @Test
    void seatMapReturnsLowerAndUpperDeck() {
        Response search = busClient.searchBus(token, ConfigManager.FROM_CITY, ConfigManager.TO_CITY, travelDate());
        search.then().statusCode(200);

        // Search response is { from, to, date, count, buses: [...] } — not a bare array.
        String busId = search.jsonPath().getString("buses[0].id");
        org.junit.jupiter.api.Assertions.assertNotNull(busId,
                "No buses returned for " + ConfigManager.FROM_CITY + "->" + ConfigManager.TO_CITY
                        + " on " + travelDate() + " — check search response shape or route/date validity.");

        busClient.getSeatMap(token, busId).then()
                .statusCode(200)
                .body("busId", equalTo(busId))
                .body("layout", equalTo("deck"))
                .body("decks.lower", notNullValue())
                .body("decks.upper", notNullValue())
                .body("decks.lower[0].seatId", notNullValue())
                .body("decks.lower[0].deck", equalTo("lower"))
                .body("decks.upper[0].seatId", notNullValue())
                .body("decks.upper[0].deck", equalTo("upper"));
    }
}