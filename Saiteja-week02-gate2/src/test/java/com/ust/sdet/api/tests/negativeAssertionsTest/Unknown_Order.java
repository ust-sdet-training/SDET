package com.ust.sdet.api.tests.negativeAssertionsTest;
import com.ust.sdet.api.API_FrameWork.Factory.SpecFactory;
import com.ust.sdet.api.API_FrameWork.config.ApiConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


public class Unknown_Order {


    private static final String BASE_URL = ApiConfig.BASE_URL;

    @BeforeAll
    static void setup() {

        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";

    }

    @Test
    @DisplayName("Checking Unknown Order")
    void unknown_order() {

        given()
                .spec(SpecFactory.opsRead())

                .when()
                .get(SpecFactory.secureOrderById(999999))

                .then()
                .spec(SpecFactory.notFound404());
    }
}
