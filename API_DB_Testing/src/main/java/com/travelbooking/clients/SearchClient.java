package com.travelbooking.clients;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import com.travelbooking.specs.RequestSpecs;

public class SearchClient extends BaseClient {

    private static final String BUSES = "/api/buses";


    public Response searchBuses(String from, String to, String date, String token) {

        return given()
                .spec(RequestSpecs.getAuthorizedRequestSpec(token))
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("date", date)
                .when()
                .get(BUSES)
                .then()
                .extract()
                .response();
    }

    public Response getBusSeatMap(String busId, String token) {

        return get("/api/buses/" + busId + "/seats", token);
    }
}