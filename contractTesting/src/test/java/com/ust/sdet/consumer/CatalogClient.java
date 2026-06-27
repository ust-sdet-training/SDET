package com.ust.sdet.consumer;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public final class CatalogClient {
    private final String baseUrl;

    public CatalogClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Product getSku(String sku) {
        return given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .get("/catalog/" + sku)
                .then()
                .statusCode(200)
                .extract()
                .as(Product.class);
    }

    public String getMissingSku(String sku) {
        Response response = given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .get("/catalog/" + sku)
                .then()
                .statusCode(404)
                .extract()
                .response();
        return response.path("message");
    }

    public static record Product(String sku, String name, int price, String availability)
    {}
}