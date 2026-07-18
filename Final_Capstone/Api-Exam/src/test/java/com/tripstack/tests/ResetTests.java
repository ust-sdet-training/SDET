package com.tripstack.tests;

import com.tripstack.config.BaseConfig;
import com.tripstack.constants.EndPoints;
import com.tripstack.utils.TokenManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class ResetTests {

    static String token;

    @BeforeAll
    static void setup() {
        BaseConfig.setup();
        token = TokenManager.getToken();
    }

    @Test
    void resetNamespace() {

        given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body("{}")
                .when()
                .post(EndPoints.RESET)
                .then()
                .statusCode(200)
                .log().all();
    }
}