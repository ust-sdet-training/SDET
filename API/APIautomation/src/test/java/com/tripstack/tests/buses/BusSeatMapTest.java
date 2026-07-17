package com.tripstack.tests.buses;

import com.tripstack.base.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class BusSeatMapTest extends BaseTest {

    @Test
    public void verifyBusSeatMap() {

        Response response =
                busClient.getBusSeats(
                        "BUS-IXCBLR-1"
                );

        response.then()
                .statusCode(200);

        response.prettyPrint();
    }
}