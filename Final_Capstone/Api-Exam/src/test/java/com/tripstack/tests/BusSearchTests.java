package com.tripstack.tests;

import com.tripstack.config.BaseConfig;
import com.tripstack.constants.EndPoints;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BusSearchTests {

    @BeforeAll
    static void setup() {
        BaseConfig.setup();
    }

    @Test
    void searchBus() {

        given()
                .queryParam("from", "HYD")
                .queryParam("to", "DEL")
                .when()
                .get(EndPoints.BUSES)
                .then()
                .statusCode(200)
                .body("count", greaterThan(0))
                .body("from", equalTo("HYD"))
                .body("to", equalTo("DEL"))
                .log().all();
    }
}