package com.ust.contract.pos;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OmsClient {

    private final String baseUrl;

    public OmsClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Order get200(int id) {
        return given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .get("/order/" + id)
                .then()
                .statusCode(200)
                .extract()
                .as(Order.class);
    }

    public Response get404(int id) {
        return given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .get("/order/" + id)
                .then()
                .statusCode(404)
                .extract()
                .response();
    }

    public static record Order(
            int orderId,
            String status,
            double total
    ) {}
}