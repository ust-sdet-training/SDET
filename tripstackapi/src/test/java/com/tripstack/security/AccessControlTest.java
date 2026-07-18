package com.tripstack.security;

import com.tripstack.base.BaseTest;
import com.tripstack.constants.ApiEndpoints;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

public class AccessControlTest extends BaseTest {

    @Test
    void verifyAccessAnotherUsersBooking() {

        RestAssured
                .given()
                .header("Authorization", "Bearer invalid-token")
                .pathParam("pnr", "PNR12345")
                .when()
                .get(ApiEndpoints.BOOKING_BY_PNR)
                .then()
                .statusCode(401);

    }

}