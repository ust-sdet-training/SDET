package tests;

import base.BaseTest;
import io.restassured.response.Response;
import models.request.CartRequest;
import models.response.CartResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.RequestSpecs;
import specs.ResponseSpecs;
import utils.TokenManager;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class CartTests extends BaseTest {

    @AfterEach
    void clearCart() {

        String token =
                TokenManager.getCustomerToken();

        given()
                .spec(
                        RequestSpecs.customerAuthSpec(token))

                .when()
                .delete("/api/cart");
    }

    @Test
    @DisplayName("Verify product can be added to cart")
    void verifyAddProductToCart() {

        String token =
                TokenManager.getCustomerToken();

        CartRequest request =
                new CartRequest(
                        101,
                        1,
                        "Standard",
                        "Black",
                        "Home delivery"
                );

        Response response =

                given()
                        .spec(
                                RequestSpecs.customerAuthSpec(token))
                        .body(request)

                        .when()
                        .post("/api/cart/items");

        response.then()
                .spec(ResponseSpecs.cartCreatedSpec);

        CartResponse cartResponse =
                response.as(CartResponse.class);

        assertAll(

                () -> assertNotNull(
                        cartResponse.getId()),

                () -> assertEquals(
                        101,
                        cartResponse.getProduct().getId()),

                () -> assertEquals(
                        1,
                        cartResponse.getQuantity())
        );
    }



    @Test
    @DisplayName("Verify invalid product cannot be added to cart")
    void verifyInvalidProduct() {

        String token =
                TokenManager.getCustomerToken();

        CartRequest request =
                new CartRequest(
                        99999,
                        1,
                        "Standard",
                        "Black",
                        "Home delivery"
                );

        given()
                .spec(
                        RequestSpecs.customerAuthSpec(token))
                .body(request)

                .when()
                .post("/api/cart/items")

                .then()
                .spec(ResponseSpecs.notFoundSpec)
                .body("message",
                        equalTo("Product not found"));
    }

    @Test
    void verifyCartSchema() {

        String token =
                TokenManager.getCustomerToken();

        given()
                .spec(
                        RequestSpecs.customerAuthSpec(token))

                .when()
                .get("/api/cart")

                .then()
                .statusCode(200)

                .body(
                        matchesJsonSchemaInClasspath(
                                "schemas/cart-schema.json"));
    }
}