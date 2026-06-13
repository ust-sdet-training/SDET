package com.ust.sdet.api.tests.negativeAssertionsTest;
import com.ust.sdet.api.API_FrameWork.Factory.SpecFactory;
import com.ust.sdet.api.API_FrameWork.config.ApiConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


public class Missing_Token {

    private static final String BASE_URL = ApiConfig.BASE_URL;

    @BeforeAll
    static void setup() {

        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";

    }

    @Test
    @DisplayName("missingToken_is401")
    void missingToken_is401() {

        given()

                .when()
                .get("/orders/5001")

                .then()
                .log().all()
                .spec(SpecFactory.unauthorized401());
    }
}
