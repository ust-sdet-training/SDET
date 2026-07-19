package org.sdet.tests.security;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class AuthorizationTest {

    @Test
    @DisplayName("Normal user cannot access Admin endpoint")
    void shouldRejectNormalUserForAdminAPI() {

        String userToken = "<Normal User Token>";

        Response response =

                given()
                        .header("Authorization", "Bearer " + userToken)
                        .when()
                        .get("/api/admin/users");

        assertEquals(
                403,
                response.statusCode(),
                "Only Admin should access this endpoint."
        );

    }

    @Test
    @DisplayName("Guest cannot access bookings")
    void shouldRejectGuestUser() {

        String guestToken = "<Guest Token>";

        Response response =

                given()
                        .header("Authorization","Bearer " + guestToken)
                        .when()
                        .get("/api/bookings");

        assertEquals(403,response.statusCode());

    }

}