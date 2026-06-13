package com.ust.sdet.tests;

import com.ust.sdet.auth.Auth;
import com.ust.sdet.base.BaseApiTest;
import com.ust.sdet.models.CartModels;
import com.ust.sdet.specs.RequestSpecs;
import com.ust.sdet.specs.ResponseSpecs;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class CartTest extends BaseApiTest {

    @Test
    @DisplayName("Add item to cart should return 201")
    void addItem_shouldReturn201() {
        CartModels.AddCartItemRequest request = new CartModels.AddCartItemRequest(101, 1, "M", "Black", "Home delivery");
        Response response = given()
                .spec(RequestSpecs.authenticatedSpec(Auth.loginToken()))
                .body(request)
                .when()
                .post("/api/cart/items")
                .then()
                .spec(ResponseSpecs.created201Spec())
                .body("id", notNullValue(), "product.id", equalTo(101), "quantity", equalTo(1))
                .body(matchesJsonSchemaInClasspath("schemas/json/cart-schema.json"))
                .extract()
                .response();


        assertStatus(response, 201);
        assertResponseBody(response);
        assertNotNull(response.path("id"));
    }


    @Test
    @DisplayName("Get cart should return 200")
    void getCart_shouldReturn200() {
        Response response = given()
                .spec(RequestSpecs.authenticatedSpec(Auth.loginToken()))
                .when()
                .get("/api/cart")
                .then()
                .spec(ResponseSpecs.ok200Spec())
                .body("items", notNullValue(), "total", greaterThanOrEqualTo(0))
                .extract()
                .response();
        assertStatus(response, 200);
    }


    @Test
    @DisplayName("Update cart item should return 200")
    void updateItem_shouldReturn200() {
        int itemId = given()
                .spec(RequestSpecs.authenticatedSpec(Auth.loginToken()))
                .body(new CartModels.AddCartItemRequest(101, 1, "M", "Black", "Home delivery"))
                .when()
                .post("/api/cart/items")
                .then()
                .extract()
                .path("id");


        CartModels.UpdateCartItemRequest request = new CartModels.UpdateCartItemRequest(2);

        given().spec(RequestSpecs.authenticatedSpec(Auth.loginToken()))
                .body(request)
                .when()
                .put("/api/cart/items/{id}", itemId)
                .then()
                .spec(ResponseSpecs.ok200Spec())
                .body("quantity", equalTo(2));
    }


    @Test
    @DisplayName("Delete cart item should return 204")
    void deleteItem_shouldReturn204() {
        int itemId = given()
                .spec(RequestSpecs.authenticatedSpec(Auth.loginToken()))
                .body(new CartModels.AddCartItemRequest(101, 1, "M", "Black", "Home delivery"))
                .when()
                .post("/api/cart/items")
                .then()
                .extract()
                .path("id");

        Response response = given()
                .spec(RequestSpecs.authenticatedSpec(Auth.loginToken()))
                .when()
                .delete("/api/cart/items/{id}", itemId)
                .then()
                .spec(ResponseSpecs.noContent204Spec())
                .extract()
                .response();
        assertStatus(response, 204);
    }


    @Test
    @DisplayName("Unauthorized cart access should return 401")
    void unauthorized_shouldReturn401() {
        given().spec(RequestSpecs.authenticatedSpec(Auth.invalidToken()))
                .when()
                .get("/api/cart")
                .then()
                .spec(ResponseSpecs.unauthorized401Spec());
    }


    @Test
    @DisplayName("Invalid product should return 404")
    void invalidProduct_shouldReturn404() {
        given().spec(RequestSpecs.authenticatedSpec(Auth.loginToken()))
                .body(new CartModels.AddCartItemRequest(99999, 1, "M", "Black", "Home delivery"))
                .when()
                .post("/api/cart/items")
                .then()
                .spec(ResponseSpecs.notFound404Spec())
                .body("message", equalTo("Product not found"));
    }


    @Test
    @DisplayName("Invalid quantity should return 400")
    void invalidQuantity_shouldReturn400() {
        given()
                .spec(RequestSpecs.authenticatedSpec(Auth.loginToken()))
                .body(new CartModels.AddCartItemRequest(101, 0, "M", "Black", "Home delivery"))
                .when()
                .post("/api/cart/items")
                .then()
                .spec(ResponseSpecs.badRequest400Spec());
    }


    @Test
    @DisplayName("Quantity exceeds stock should return 409")
    void stockExceeded_shouldReturn409() {
        given()
                .spec(RequestSpecs.authenticatedSpec(Auth.loginToken()))
                .body(new CartModels.AddCartItemRequest(103, 100, "M", "Black", "Home delivery"))
                .when()
                .post("/api/cart/items")
                .then()
                .spec(ResponseSpecs.badRequest400Spec())
                .body("message", containsString("between 1 and 5"));
    }
}