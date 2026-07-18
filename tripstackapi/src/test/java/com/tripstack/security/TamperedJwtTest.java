package com.tripstack.security;

import com.tripstack.base.BaseTest;
import com.tripstack.base.RequestSpecificationBuilder;
import com.tripstack.constants.ApiEndpoints;
import com.tripstack.services.AuthService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TamperedJwtTest extends BaseTest {

    private final AuthService authService = new AuthService();

    @Test
    @DisplayName("Verify Tampered JWT Returns Unauthorized")
    void verifyTamperedJwt() {

        String token = authService.getToken();

        String tamperedToken =
                token.substring(0, token.length() - 5) + "ABCDE";

        RestAssured
                .given()
                .spec(RequestSpecificationBuilder.requestSpecification())
                .header("Authorization", "Bearer " + tamperedToken)
                .when()
                .get(ApiEndpoints.BOOKINGS)
                .then()
                .statusCode(401);

    }

}