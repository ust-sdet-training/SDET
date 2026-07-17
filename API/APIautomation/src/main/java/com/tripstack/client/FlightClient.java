package com.tripstack.client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class FlightClient {

    public Response searchFlights(
            String from,
            String to,
            String date
    ) {

        return given()
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("date", date)
                .when()
                .get("/flights");
    }

    public Response getSeatMap(
            String flightId
    ) {

        return given()
                .pathParam(
                        "id",
                        flightId
                )
                .when()
                .get("/flights/{id}/seats");


    }
}