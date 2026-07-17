package com.travelbooking.clients;

import com.travelbooking.specs.RequestSpecs;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public abstract class BaseClient {

    protected Response get(String endpoint) {

        return given()
                .spec(RequestSpecs.getRequestSpec())
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    protected Response get(String endpoint, String token) {

        return given()
                .spec(RequestSpecs.getAuthorizedRequestSpec(token))
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    protected Response post(String endpoint, Object body) {

        return given()
                .spec(RequestSpecs.getRequestSpec())
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    protected Response post(String endpoint, Object body, String token) {

        return given()
                .spec(RequestSpecs.getAuthorizedRequestSpec(token))
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    protected Response post(String endpoint, String token) {

        return given()
                .spec(RequestSpecs.getAuthorizedRequestSpec(token))
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    protected Response put(String endpoint, Object body, String token) {

        return given()
                .spec(RequestSpecs.getAuthorizedRequestSpec(token))
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    protected Response delete(String endpoint, String token) {

        return given()
                .spec(RequestSpecs.getAuthorizedRequestSpec(token))
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }
}