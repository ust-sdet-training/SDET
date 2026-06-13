package com.ust.sdet.api.apiframework.support;

import com.ust.sdet.api.apiframework.specs.SpecFactory;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public final class ApiSupport {

    private ApiSupport() {}

    public static Response createOrder(Map<String, Object> order, String token) {
        return given()
                .spec(SpecFactory.secureOrders(token))
                .body(order)
                .when()
                .post()
                .then()
                .extract()
                .response();
    }

    // --- user-facing helpers (login, cart, place order) for reuse ---
    public static String loginAndGetToken() {
        return given()
                .spec(SpecFactory.jsonSpec("/api"))
                .body(Map.of("email", "customer@example.com", "password", "Password@123"))
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    public static Response clearCart(String token) {
        return given()
                .spec(SpecFactory.authSpec("/api/cart", token))
                .when()
                .delete()
                .then()
                .extract()
                .response();
    }

    public static Response addItemToCart(String token, Map<String, Object> item) {
        return given()
                .spec(SpecFactory.authSpec("/api/cart/items", token))
                .body(item)
                .when()
                .post()
                .then()
                .extract()
                .response();
    }

    public static Response placeOrderAsUser(String token, Map<String, Object> order) {
        return given()
                .spec(SpecFactory.authSpec("/api/orders", token))
                .body(order)
                .when()
                .post()
                .then()
                .extract()
                .response();
    }

    public static Response allocateOrder(long id, String token) {
        return given()
                .spec(SpecFactory.secureOrders(token))
                .when()
                .post("/{id}/allocate", id)
                .then()
                .extract()
                .response();
    }

    public static Response shipOrder(long id, String token) {
        return given()
                .spec(SpecFactory.secureOrders(token))
                .when()
                .post("/{id}/ship", id)
                .then()
                .extract()
                .response();
    }

    public static Response fetchOrder(long id, String token) {
        return given()
                .spec(SpecFactory.secureOrders(token))
                .when()
                .get("/{id}", id)
                .then()
                .extract()
                .response();
    }
}
