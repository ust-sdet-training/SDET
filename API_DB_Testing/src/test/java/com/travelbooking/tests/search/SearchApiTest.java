package com.travelbooking.tests.search;

import com.travelbooking.base.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SearchApiTest extends BaseTest {

    @Test
    void searchBuses() {

        Response response = searchClient.searchBuses(
                "LKO",
                "BLR",
                "2026-08-12",
                token
        );

        assertEquals(200, response.getStatusCode());
        assertFalse(response.asString().isBlank());
    }

    @Test
    void getBusSeatMap() {

        Response searchResponse = searchClient.searchBuses(
                "LKO",
                "BLR",
                "2026-08-12",
                token
        );

        String busId = searchResponse.jsonPath().getString("buses[0].id");

        Response response = searchClient.getBusSeatMap(busId, token);

        assertEquals(200, response.getStatusCode());
        assertFalse(response.asString().isBlank());
    }
}