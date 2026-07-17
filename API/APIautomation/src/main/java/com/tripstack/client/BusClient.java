package com.tripstack.client;

import com.tripstack.config.ConfigManager;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BusClient {

    @Step("GET /buses?from={from}&to={to}&date={date}")
    public Response searchBus(String token, String from, String to, String date) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .header("Authorization", "Bearer " + token)
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("date", date)
                .when()
                .get("/buses");
    }

    @Step("GET /buses/{busId}/seats")
    public Response getSeatMap(String token, String busId) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/buses/" + busId + "/seats");
    }
}