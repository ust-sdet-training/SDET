package com.ust.capstone.api;

import io.restassured.response.Response;


import static com.ust.capstone.api.ApiSpec.requestSpec;
import static io.restassured.RestAssured.given;

public class BusClient {
    public Response search(String from, String to, String date) {
        return given()
                .spec(requestSpec())
                        .queryParam("from", from)
                        .queryParam("to", to)
                        .queryParam("date", date)
                        .when()
                        .get("/buses");
    }
}