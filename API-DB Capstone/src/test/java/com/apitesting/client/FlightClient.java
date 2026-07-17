package com.apitesting.client;

import com.apitesting.support.builders.ApiSpecBuilders;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class FlightClient {

    public Response searchFlights(String from, String to, String date, int pax, String cabinClass) {
        return given()
                .spec(ApiSpecBuilders.requestSpec())
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("date", date)
                .queryParam("pax", pax)
                .queryParam("class", cabinClass)
                .when()
                .get("/flights");
    }

    public Response getSeatMap(String flightId, String cabinClass) {
        return given()
                .spec(ApiSpecBuilders.requestSpec())
                .queryParam("class", cabinClass)
                .when()
                .get("/flights/" + flightId + "/seats");
    }
}