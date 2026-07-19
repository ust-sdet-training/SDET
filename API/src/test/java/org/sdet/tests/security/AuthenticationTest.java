package org.sdet.tests.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sdet.API_Framework.constants.Endpoints;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class AuthenticationTest {

    @Test
    @DisplayName("Access protected endpoint without JWT")
    void shouldRejectRequestWithoutToken() {

        Response response =

                given()
                        .contentType("application/json")
                        .when()
                        .get(Endpoints.MY_BOOKINGS);

        assertEquals(
                401,
                response.statusCode(),
                "API should reject unauthenticated requests."
        );

    }

    @Test
    @DisplayName("Access protected endpoint with invalid JWT")
    void shouldRejectInvalidToken() {

        Response response =

                given()
                        .header("Authorization", "Bearer InvalidJWT")
                        .when()
                        .get(Endpoints.MY_BOOKINGS);

        assertEquals(401, response.statusCode());

    }

    @Test
    @DisplayName("Access protected endpoint with malformed JWT")
    void shouldRejectMalformedToken() {

        Response response =

                given()
                        .header("Authorization", "Bearer abc.xyz")
                        .when()
                        .get(Endpoints.MY_BOOKINGS);

        assertEquals(401, response.statusCode());

    }

}