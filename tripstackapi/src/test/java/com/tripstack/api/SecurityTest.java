package com.tripstack.api;

import com.tripstack.base.BaseTest;
import com.tripstack.services.AuthService;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class SecurityTest extends BaseTest {

    private final AuthService authService = new AuthService();

    @Test
    void verifyInvalidToken() {

        given()
                .header("Authorization", "Bearer InvalidToken")
                .when()
                .get("/api/auth/me")
                .then()
                .statusCode(401);

    }

    @Test
    void verifyMissingToken() {

        given()
                .when()
                .get("/api/auth/me")
                .then()
                .statusCode(401);

    }

    @Test
    void verifyTravellerCannotAccessAdminEndpoint() {

        String token = authService.getToken();

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/auth/admin-ping")
                .then()
                .statusCode(403);

    }

}