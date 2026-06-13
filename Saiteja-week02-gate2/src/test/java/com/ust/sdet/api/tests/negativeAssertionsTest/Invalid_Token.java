package com.ust.sdet.api.tests.negativeAssertionsTest;
import com.ust.sdet.api.API_FrameWork.Factory.SpecFactory;
import com.ust.sdet.api.API_FrameWork.config.ApiConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


import java.util.ArrayList;
import java.util.List;

public class Invalid_Token {


    private static final String BASE_URL = ApiConfig.BASE_URL;

    @BeforeAll
    static void setup() {

        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";

    }

    @Test
    @DisplayName("invalidToken_is401")
    void invalidToken_is401() {

        given()
                .auth()
                .oauth2("garbage-token")

                .when()
                .get("/orders/5001")

                .then()
                .log().all()
                .spec(SpecFactory.unauthorized401());
    }
}
