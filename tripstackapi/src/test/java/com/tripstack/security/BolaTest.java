package com.tripstack.security;

import com.tripstack.base.BaseTest;
import com.tripstack.base.RequestSpecificationBuilder;
import com.tripstack.constants.ApiEndpoints;
import com.tripstack.services.AuthService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BolaTest extends BaseTest {

    private final AuthService authService = new AuthService();

    @Test
    @DisplayName("Verify user cannot access another employee booking")
    void verifyCannotAccessAnotherEmployeeBooking() {

        String token = authService.getToken();

        RestAssured
                .given()
                .spec(RequestSpecificationBuilder.authorizedRequest(token))
                .pathParam("pnr", "TS-1023-0001")
                .when()
                .get(ApiEndpoints.BOOKING_BY_PNR)
                .then()
                .statusCode(403);

    }

}