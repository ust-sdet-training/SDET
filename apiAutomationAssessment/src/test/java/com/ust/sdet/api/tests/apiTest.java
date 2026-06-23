package com.apimocktest.tests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.apimocktest.models.Data.*;

import static com.apimocktest.support.requestSpecifications.auth;
import static com.apimocktest.support.responseSpecifications.response200;
import static com.apimocktest.support.responseSpecifications.response201;
import static com.apimocktest.support.responseSpecifications.response204;
import static com.apimocktest.support.responseSpecifications.responseput200;
import static io.restassured.RestAssured.given;

public class apiTest {

    @Test
    @DisplayName("Get users")
    void testGet(){
        given()
            .spec(auth)
        .when()
            .get("/users")
        .then()
            .spec(response200);
    }

    @Test
    @DisplayName("Post Method")
    void testPost(){
        given()
            .spec(auth)
            .body(PostOrders)
        .when()
            .post("/test-suite/collections/users/records")
        .then()
            .spec(response201);
    }
    @Test
    @DisplayName("Put Method")
    void testPut(){
        given()
            .spec(auth)
            .body(PutOrders)
        .when()
            .put("/test-suite/collections/users/records/rec_ve8ayrjj")
        .then()
            .spec(responseput200);
    }

    @Test
    @DisplayName("Put Method")
    void testDelete(){
        given()
            .spec(auth)
        .when()
            .delete("/users")
        .then()
            .spec(response204);
    }

}
