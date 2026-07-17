package com.tripstack.api.specs;

import com.tripstack.config.TestConfig;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BookingRequestSpec {
    public static RequestSpecification publicSpec() {
        return given()
                .baseUri(TestConfig.getBaseUrl())
                .basePath("/api")
                .accept("application/json")
                .contentType("application/json");
    }

    public static RequestSpecification authenticatedSpec(String token) {
        return publicSpec().header("Authorization", "Bearer " + token);
    }
}
