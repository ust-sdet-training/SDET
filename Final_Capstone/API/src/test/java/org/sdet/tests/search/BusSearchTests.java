package org.sdet.tests.search;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.sdet.base.BaseTest;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class BusSearchTests extends BaseTest {

    @Test
    public void verifyBusSearch() {

        Response response = searchClient.searchBuses(
                "Hyderabad",
                "Bangalore",
                "2026-07-25"
        );

        response.then()
                .statusCode(200)
                .body("buses", notNullValue());

        assertFalse(
                response.jsonPath().getList("buses").isEmpty(),
                "No buses found"
        );

        String busId = response.jsonPath().getString("buses[0].id");

        assertNotNull(busId);
    }
}