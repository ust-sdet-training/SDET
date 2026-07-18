package com.tripstack.security;

import com.tripstack.base.BaseTest;
import com.tripstack.constants.ApiEndpoints;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

public class InvalidTokenTest extends BaseTest {

    @Test
    void verifyInvalidToken() {

        RestAssured
                .given()
                .header("Authorization", "Bearer invalid-token")
                .when()
                .get(ApiEndpoints.BOOKINGS)
                .then()
                .statusCode(401);

    }

}