package com.ust.sdet.api.client;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

public class ApiClient {

    public Response get(String path, RequestSpecification requestSpecification) {
        return given()
                .spec(requestSpecification)
                .when()
                .get(path)
                .then()
                .extract()
                .response();
    }

    public Response get(String path, RequestSpecification requestSpecification, ResponseSpecification responseSpecification) {
        return given()
                .spec(requestSpecification)
                .when()
                .get(path)
                .then()
                .spec(responseSpecification)
                .extract()
                .response();
    }

    public Response post(String path, Object body, RequestSpecification requestSpecification) {
        RequestSpecification spec = given().spec(requestSpecification);
        if (body != null) {
            spec.body(body);
        }
        return spec.when()
                .post(path)
                .then()
                .extract()
                .response();
    }

    public Response post(String path, RequestSpecification requestSpecification) {
        return given()
                .spec(requestSpecification)
                .when()
                .post(path)
                .then()
                .extract()
                .response();
    }

    public Response post(String path, Object body, RequestSpecification requestSpecification, ResponseSpecification responseSpecification) {
        RequestSpecification spec = given().spec(requestSpecification);
        if (body != null) {
            spec.body(body);
        }
        return spec.when()
                .post(path)
                .then()
                .spec(responseSpecification)
                .extract()
                .response();
    }
}
