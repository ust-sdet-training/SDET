package com.ust.sdet.api.tests.negativeAssertionsTest;
import com.ust.sdet.api.API_FrameWork.Factory.SpecFactory;
import com.ust.sdet.api.API_FrameWork.config.ApiConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Invalid_Life_Cycle {

    private final List<Long> createdOrderIds = new ArrayList<>();

    private static final String BASE_URL = ApiConfig.BASE_URL;

    @BeforeAll
    static void setup() {

        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";

    }

    @Test
    @DisplayName("Cannot Ship Before Allocation")
    void shouldReturn409WhenShippingBeforeAllocation() {

        var createOrderRequest = Map.of(
                "items", new int[]{101},
                "currency", "INR"
        );

        Response createResponse =
                given()
                        .spec(SpecFactory.opsWrite())
                        .body(createOrderRequest)

                        .when()
                        .post(SpecFactory.secureOrdersPath())

                        .then()
                        .spec(SpecFactory.created201())
                        .extract()
                        .response();

        long orderId = createResponse.jsonPath().getLong("id");

        createdOrderIds.add(orderId);

        given()
                .spec(SpecFactory.opsWrite())

                .when()
                .post(SpecFactory.shipOrderPath(orderId))

                .then()
                .spec(SpecFactory.conflict409())
                .body("currentStatus", equalTo("CREATED"))
                .body("expectedStatus", equalTo("ALLOCATED"));
    }
}