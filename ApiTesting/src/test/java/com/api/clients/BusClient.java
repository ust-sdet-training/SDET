package com.api.clients;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BusClient {

    public Response searchBuses(String from, String to, String date) {

        return given()
                .spec(ApiSpec.requestspec())
                .basePath("/buses")
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("date", date)
                .when()
                .get();
    }

    public Response getBusSeatMap(String busId) {

        return given()
                .spec(ApiSpec.requestspec())
                .basePath("/buses/" + busId + "/seats")
                .when()
                .get();
    }
}