package com.apitesting.client;

import com.apitesting.support.builders.ApiSpecBuilders;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OpsClient {

    public Response reset(String token) {

        return given()
                .spec(ApiSpecBuilders.authSpec(token))
                .when()
                .post("/reset");
    }
}