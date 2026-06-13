package services;

import io.restassured.response.Response;
import models.OrderRequest;
import specs.RequestSpecs;

import static io.restassured.RestAssured.given;

public class OrderService {

    private OrderService() {
    }

    public static Response createSecureOrder(
            String token,
            OrderRequest request) {

        return given()
                .spec(RequestSpecs.authenticatedRequest(token))
                .body(request)
                .when()
                .post("/api/secure/orders");
    }

    public static Response getSecureOrder(
            String token,
            int orderId) {

        return given()
                .spec(RequestSpecs.authenticatedRequest(token))
                .when()
                .get("/api/secure/orders/{id}", orderId);
    }

    public static Response allocateOrder(
            String token,
            int orderId) {

        return given()
                .spec(RequestSpecs.authenticatedRequest(token))
                .when()
                .post("/api/secure/orders/{id}/allocate", orderId);
    }

    public static Response shipOrder(
            String token,
            int orderId) {

        return given()
                .spec(RequestSpecs.authenticatedRequest(token))
                .when()
                .post("/api/secure/orders/{id}/ship", orderId);
    }

    public static Response deleteOrder(
            String token,
            int orderId) {

        return given()
                .spec(RequestSpecs.authenticatedRequest(token))
                .when()
                .delete("/api/secure/orders/{id}", orderId);
    }
}