package com.api.clients;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ResetClient {

    public Response resetNamespace(String token) {

        return given()
                .spec(ApiSpec.requestspec())
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/reset");
    }

    public Response resetAnotherNamespace(String token, String empId) {

        return given()
                .spec(ApiSpec.requestspec())
                .header("Authorization", "Bearer " + token)
                .queryParam("emp", empId)
                .when()
                .post("/reset");
    }
}