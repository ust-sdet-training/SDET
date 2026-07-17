package org.sdet.clients;

import io.restassured.response.Response;
import org.sdet.base.BaseAPI;
import org.sdet.constants.Endpoints;
import org.sdet.utils.TokenManager;

import static io.restassured.RestAssured.given;

public class SearchClient extends BaseAPI {

    public Response searchFlights(String from, String to, String date) {

        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + TokenManager.getToken())
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("date", date)

                .when()
                .get(Endpoints.FLIGHTS)

                .then()
                .extract()
                .response();
    }

    public Response searchBuses(String from, String to, String date) {

        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + TokenManager.getToken())
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("date", date)

                .when()
                .get(Endpoints.BUSES)

                .then()
                .extract()
                .response();
    }

}