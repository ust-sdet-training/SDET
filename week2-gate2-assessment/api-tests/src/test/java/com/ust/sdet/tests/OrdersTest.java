package com.ust.sdet.tests;

import com.ust.sdet.auth.Auth;
import com.ust.sdet.base.BaseApiTest;

import com.ust.sdet.config.TestConfig;
import com.ust.sdet.models.CartModels;
import com.ust.sdet.models.OrderModels;

import com.ust.sdet.specs.RequestSpecs;
import com.ust.sdet.specs.ResponseSpecs;

import io.restassured.response.Response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class OrdersTest extends BaseApiTest {
    private void addItemToCart() {
        given()
                .spec(RequestSpecs.authenticatedSpec(Auth.loginToken()))
                .body(new CartModels.AddCartItemRequest(
                                101,
                                1,
                                "M",
                                "Black",
                                "Home delivery"
                        )
                )
                .when()
                .post("/api/cart/items")
                .then()
                .spec(ResponseSpecs.created201Spec());
    }

    @Test
    @DisplayName("Create order should return 201")
    void createOrder_shouldReturn201() {
        addItemToCart();
        OrderModels.CreateOrderRequest request = new OrderModels.CreateOrderRequest(
                "Credit card",
                "Bangalore",
                "Tomorrow"
        );

        Response response =
                given()
                        .spec(RequestSpecs.authenticatedSpec(Auth.loginToken()))
                        .body(request)
                        .when()
                        .post("/api/orders")
                        .then()
                        .spec(ResponseSpecs.created201Spec())
                        .body("status",
                                equalTo("Confirmed"),
                                "payment",
                                equalTo("Paid"),
                                "address",
                                equalTo("Bangalore")
                        )
                        .body(matchesJsonSchemaInClasspath(
                                        "schemas/json/order-schema.json"
                                )
                        )
                        .extract()
                        .response();

        assertStatus(response, 201);
        assertLocationHeader(response);
    }

    @Test
    @DisplayName("Create order with empty cart should return 400")
    void emptyCart_shouldReturn400() {
        given()
                .spec(RequestSpecs.authenticatedSpec(Auth.loginToken()))
                .when()
                .delete("/api/cart")
                .then()
                .statusCode(204);

        given()
                .spec(RequestSpecs.authenticatedSpec(Auth.loginToken()))
                .body(new OrderModels.CreateOrderRequest(
                                "Credit card",
                                "Bangalore",
                                "Tomorrow"
                        )
                )
                .when()
                .post("/api/orders")
                .then()
                .spec(ResponseSpecs.badRequest400Spec())
                .body("message",
                        equalTo("Cannot create an order from an empty cart")
                );
    }

    @Test
    @DisplayName("Fetch orders should return 200")
    void fetchOrders_shouldReturn200() {
        given()
                .spec(RequestSpecs.authenticatedSpec(Auth.loginToken()))
                .when()
                .get("/api/orders")
                .then()
                .spec(ResponseSpecs.ok200Spec())
                .body("items", notNullValue());
    }

    @Test
    @DisplayName("Fetch invalid order should return 404")
    void fetchInvalidOrder_shouldReturn404() {

        given()
                .spec(RequestSpecs.authenticatedSpec(Auth.loginToken()))
                .when()
                .get("/api/orders/999999")
                .then()
                .spec(ResponseSpecs.notFound404Spec())
                .body("message", equalTo("Order not found"));
    }

    @Test
    @DisplayName("Create secure order should return 201")
    void createSecureOrder_shouldReturn201() {

        Response response =
                given()
                        .spec(RequestSpecs.authenticatedSpec(Auth.oauthToken()))
                        .body(new OrderModels.SecureOrderRequest(
                                        List.of(101, 107)
                                )
                        )
                        .when()
                        .post("/api/secure/orders")
                        .then()
                        .spec(ResponseSpecs.created201Spec())
                        .body("status", equalTo("CREATED"))
                        .body(matchesJsonSchemaInClasspath(
                                        "schemas/json/secure-order-schema.json"
                                )
                        )
                        .extract()
                        .response();

        assertStatus(response, 201);
    }

    @Test
    @DisplayName("Viewer token should return 403")
    void viewerCannotCreateSecureOrder() {

        given()
                .spec(RequestSpecs.authenticatedSpec(Auth.viewerOauthToken()))
                .body(new OrderModels.SecureOrderRequest(List.of(101)))
                .when()
                .post("/api/secure/orders")
                .then()
                .spec(ResponseSpecs.forbidden403Spec());
    }

    @Test
    @DisplayName("Expired token should return 401")
    void expiredToken_shouldReturn401() {

        given()
                .spec(RequestSpecs.authenticatedSpec(Auth.expiredOauthToken()))
                .when()
                .get("/api/secure/orders/5001")
                .then()
                .spec(ResponseSpecs.unauthorized401Spec());
    }

    @Test
    @DisplayName("Allocate and ship full flow")
    void allocateShipFlow_shouldReturn200() {
        int id = given()
                .spec(RequestSpecs.authenticatedSpec(Auth.oauthToken()))
                .body(new OrderModels.SecureOrderRequest(
                                List.of(101)
                        )
                )
                .when()
                .post("/api/secure/orders")
                .then()
                .extract()
                .path("id");

        given()
                .spec(RequestSpecs.authenticatedSpec(Auth.oauthToken()))
                .when()
                .post("/api/secure/orders/{id}/allocate", id)
                .then()
                .spec(ResponseSpecs.ok200Spec())
                .body("status", equalTo("ALLOCATED"));

        given()
                .spec(RequestSpecs.authenticatedSpec(Auth.oauthToken()))
                .when()
                .post("/api/secure/orders/{id}/ship", id)
                .then()
                .spec(ResponseSpecs.ok200Spec())
                .body("status", equalTo("SHIPPED"));

        given()
                .spec(RequestSpecs.authenticatedSpec(Auth.oauthToken()))
                .when()
                .get("/api/secure/orders/{id}", id)
                .then()
                .spec(ResponseSpecs.ok200Spec())
                .body("status", equalTo("SHIPPED"));
    }

    @Test
    @DisplayName("Ship without allocate should return 409")
    void invalidTransition_shouldReturn409() {

        int id =
                given()
                        .spec(RequestSpecs.authenticatedSpec(Auth.oauthToken()))
                        .body(new OrderModels.SecureOrderRequest(
                                List.of(101))
                        )
                        .when()
                        .post("/api/secure/orders")
                        .then()
                        .extract()
                        .path("id");

        given()
                .spec(RequestSpecs.authenticatedSpec(Auth.oauthToken()))
                .when()
                .post("/api/secure/orders/{id}/ship", id)
                .then()
                .spec(ResponseSpecs.conflict409Spec())
                .body("currentStatus", equalTo("CREATED")
                );
    }
    @Test
    @DisplayName("Partner order should return 200")
    void partnerOrder_shouldReturn200() {

        given()

                .header("x-api-key",
                        TestConfig.RETAIL_API_KEY
                )

                .when()

                .get(
                        "/api/partner/orders/5001"
                )

                .then()

                .spec(
                        ResponseSpecs.ok200Spec()
                )

                .body(
                        "partner",
                        equalTo(
                                "UST Partner Channel"
                        )
                )

                .body(
                        "order.id",
                        equalTo(
                                5001
                        )
                );

    }

    @Test
    void missingApiKey_shouldReturn401() {

        given()

                .when()

                .get(
                        "/api/partner/orders/5001"
                )

                .then()

                .spec(
                        ResponseSpecs.unauthorized401Spec()
                )

                .body(
                        "message",
                        equalTo(
                                "API key required"
                        )
                );

    }

}