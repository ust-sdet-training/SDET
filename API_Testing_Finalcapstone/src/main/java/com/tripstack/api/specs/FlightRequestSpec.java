package com.tripstack.api.specs;

import com.tripstack.config.TestConfig;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class FlightRequestSpec {
    public static RequestSpecification publicSpec() {
        return given()
                .baseUri(TestConfig.getBaseUrl())
                .basePath("/api")
                .accept("application/json");
    }
}
