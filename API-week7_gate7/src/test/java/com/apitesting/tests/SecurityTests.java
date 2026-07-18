package com.apitesting.tests;

import com.apitesting.support.builders.ApiSpecBuilders;
import org.junit.jupiter.api.Test;

import static com.apitesting.support.builders.ApiSpecBuilders.invalidokenreqSpecget;
import static com.apitesting.support.builders.ApiSpecBuilders.notokenreqSpecget;
import static io.restassured.RestAssured.given;

public class SecurityTests {

    @Test
    void bolaReadAnotherUsersPnr() {

        String token = ApiTesting.tokenGenerate();

        given()
                .spec(ApiSpecBuilders.reqSpecget(token))
                .when()
                .get("/bookings/TS-1004-0001")
                .then()
                .statusCode(403)
                .log().body();

    }

    @Test
    void accessWithoutToken() {

        given()
                .spec(notokenreqSpecget())
                .when()
                .get("/bookings")
                .then()
                .statusCode(401);
    }

    @Test
    void invalidToken() {

        given()
                .spec(invalidokenreqSpecget())
                .when()
                .get("/bookings")
                .then()
                .statusCode(401);
    }

    @Test
    void invalidPnr() {

        String token = ApiTesting.tokenGenerate();

        given()
                .spec(ApiSpecBuilders.reqSpecget(token))
                .when()
                .get("/bookings/TS-9999-9999")
                .then()
                .statusCode(404);
    }
}
