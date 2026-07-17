package org.sdet.tests.security;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.sdet.base.BaseAPI;
import org.sdet.constants.Endpoints;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class SecurityTests extends BaseAPI {

    @Test
    public void verifyAccessWithoutToken() {

        Response response = given()
                .spec(requestSpec)

                .when()
                .get(Endpoints.BOOKINGS);

        response.then()
                .statusCode(401);
    }

    @Test
    public void verifyAccessWithInvalidToken() {

        Response response = given()
                .spec(requestSpec)
                .header("Authorization", "Bearer invalid_token")

                .when()
                .get(Endpoints.BOOKINGS);

        response.then()
                .statusCode(401);
    }

    @Test
    public void verifyProtectedEndpointRequiresAuthentication() {

        Response response = given()
                .spec(requestSpec)

                .when()
                .post(Endpoints.BOOKINGS);

        response.then()
                .statusCode(401);
    }

    @Test
    public void verifySqlInjectionAttempt() {

        Response response = given()
                .spec(requestSpec)
                .queryParam("from", "' OR 1=1 --")
                .queryParam("to", "Delhi")
                .queryParam("date", "2026-07-25")

                .when()
                .get(Endpoints.FLIGHTS);

        response.then()
                .statusCode(400);
    }

    @Test
    public void verifyXssInputRejected() {

        Response response = given()
                .spec(requestSpec)
                .queryParam("from", "<script>alert('xss')</script>")
                .queryParam("to", "Delhi")
                .queryParam("date", "2026-07-25")

                .when()
                .get(Endpoints.FLIGHTS);

        response.then()
                .statusCode(anyOf(
                        org.hamcrest.Matchers.is(400),
                        org.hamcrest.Matchers.is(422)
                ));
    }
}