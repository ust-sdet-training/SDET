package com.tripstack.security;

import com.tripstack.base.BaseTest;
import com.tripstack.constants.ApiEndpoints;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

public class UnauthorizedAccessTest extends BaseTest {

    @Test
    void verifyBookingWithoutToken() {

        RestAssured
                .given()
                .contentType("application/json")
                .when()
                .get(ApiEndpoints.BOOKINGS)
                .then()
                .statusCode(401);

    }

}