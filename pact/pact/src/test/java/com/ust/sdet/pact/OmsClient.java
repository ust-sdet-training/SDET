package com.ust.sdet.pact;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

final class OmsClient {


    private String baseUrl;

    public OmsClient(String baseUrl){
        this.baseUrl=baseUrl;
    }

    public Order getOrder(int id) {

        return  given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .get("/order/" + id)
                .then()
                .statusCode(200)
                .extract()
                .as(Order.class);
    }
    public Order createOrder(Order order) {
        return  given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(order)
                .post("/orders/")
                .then()
                .statusCode(201)
                .extract()
                .as(Order.class);
    }

    public Response getInventory() {
        return  given()
                .baseUri(baseUrl)
                .get("/inventory/SKU-9")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    public static record Order(int statusCode,int orderId,String status,double total){}

    public Response getForbiddenOrder() {

        return given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .get("/order/999")
                .then()
                .statusCode(403)
                .extract()
                .response();
    }

    public Response getMissingOrder() {

        return given()
                .baseUri(baseUrl)
                .get("/order/404")
                .then()
                .statusCode(404)
                .extract()
                .response();
    }


}
