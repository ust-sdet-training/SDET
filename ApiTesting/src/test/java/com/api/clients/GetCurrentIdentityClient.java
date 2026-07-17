package com.api.clients;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetCurrentIdentityClient {

    public Response getCurrentIdentity(String token) {
        return given()
                .spec(ApiSpec.requestspec())
                .basePath("/auth/me")
                .header("Authorization", "Bearer " + token)
                .when()
                .get();
    }
}