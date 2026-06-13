package com.ust.sdet.api.tests.negativeAssertionsTest;
import com.ust.sdet.api.API_FrameWork.Factory.SpecFactory;
import com.ust.sdet.api.API_FrameWork.config.ApiConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


import java.util.List;
import java.util.Map;

public class Wrong_Role {


    private static final String BASE_URL = ApiConfig.BASE_URL;

    @BeforeAll
    static void setup() {

        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";

    }

    @Test
    @DisplayName("Checking with Wrong Role")
    void wrongRole_is403() {

        given()
                .auth()
                .oauth2(SpecFactory.getViewerToken())
                .body(Map.of(
                        "items", List.of(101),
                        "currency", "INR"
                ))

                .when()
                .post("/secure/orders")

                .then()
                .spec(SpecFactory.forbidden403());
    }
}
