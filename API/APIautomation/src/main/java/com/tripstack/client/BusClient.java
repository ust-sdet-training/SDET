package com.tripstack.client;

import com.tripstack.config.ConfigManager;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BusClient {

    private final String BASE_URL =
            ConfigManager.get("base.url");

    public Response searchBuses(
            String from,
            String to,
            String date
    ) {

        return given()
                .baseUri(BASE_URL)
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("date", date)
                .log().all()
                .when()
                .get("/api/buses")
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Response getBusSeats(
            String busId
    ) {

        return given()
                .baseUri(BASE_URL)
                .pathParam("id", busId)
                .log().all()
                .when()
                .get("/api/buses/{id}/seats")
                .then()
                .log().all()
                .extract()
                .response();
    }
}