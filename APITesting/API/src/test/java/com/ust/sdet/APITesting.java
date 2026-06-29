package com.ust.sdet;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class APITesting {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    void shouldReturnValidPostDetails() {
        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", notNullValue())
                .body("title", not(emptyOrNullString()))
                .body("body", not(emptyOrNullString()));
    }
    @Test
    void shouldReturn404ForNonExistPost() {
        given()
                .when()
                .get("/posts/9999")
                .then()
                .statusCode(404)
                .body(equalTo("{}"));
    }
}