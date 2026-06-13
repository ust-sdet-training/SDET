package com.ust.sdet.api;

import com.mysql.cj.protocol.x.XMessage;
import com.ust.sdet.api.support.Credentials;
import com.ust.sdet.api.support.DbSupport;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.ust.sdet.api.specification.SpecFactory.*;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class week2_test {
    static String TOKEN;

    @BeforeAll
    static void setup(){
        TOKEN = token();
    }

    @Test
    @DisplayName("T1: Login Test")
    void loginNotNull(){
        given()
                .baseUri(Credentials.BASE_URL)
                .basePath("/api")
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .accept(ContentType.JSON)
                .formParam("grant_type", "client_credentials")
                .auth().preemptive().basic(Credentials.OAUTH_CLIENT_ID,Credentials.OAUTH_CLIENT_SECRET)
                .when()
                .post("/oauth/token")
                .then()
                .body("access_token",notNullValue());
    }

    @Test
    @DisplayName("T2: Login Test")
    void login(){
        var loginCred = Map.of("email", "customer@example.com","password", "Password@123");
        given()
                .spec(loginSpec("/auth/login"))
                .body(loginCred)
                .when()
                .post()
                .then()
                .spec(resSpec())
                .body("token",notNullValue())
                .body("user", notNullValue())
                .body("user.id", notNullValue());
    }

    @Test
    @DisplayName("T3: Get /Products All")
    void getProducts(){
        given()
                .spec(reqSpec("/products", TOKEN))
                .when()
                .get()
                .then()
                .spec(resSpec())
                .body(matchesJsonSchemaInClasspath("schemas/json/productList.schema.json"))
                .body("total",greaterThanOrEqualTo(1))
                .body("items.size()", greaterThan(0) );
    }

    @Test
    @DisplayName("T4: Post Cart-Items")
    void cartItems() {
        var cart = Map.of("productId", 103 , "quantity", 1, "size", "UK 9", "color", "Navy", "fulfilment", "Home delivery");
        given()
                .spec(reqSpec("/cart/items", TOKEN))
                .body(cart)
                .when()
                .post()
                .then()
                .spec(orderResSpec())
                .body("productId", equalTo(cart.get("productId")));

    }

//    @Test
//    @DisplayName("T5: Post Cart-Items - status 409")
//    void cartItems409() {
//        var cart = Map.of("productId", 101  , "quantity", 2, "size", "UK 9", "color", "Navy", "fulfilment", "Home delivery");
//        String msg = given()
//                .spec(reqSpec("/cart/items",TOKEN))
//                .body(cart)
//                .when()
//                .post()
//                .then()
//                .spec(cartResSpec())
//                .extract()
////                .path("message").toString();
//                .jsonPath().getString("message");
//
//        assertEquals("Maximum quantity per cart line is 5", msg);
//    }

    @Test
    @DisplayName("T6: Post an item to order")
    void storeProductInOrder(){
        var order = Map.of(
                "productId", List.of(101,106),
                "currency", "INR"
        );
        given()
                .spec(reqSpec("/secure/orders", TOKEN))
                .body(order)
                .when()
                .post()
                .then()
                .spec(orderResSpec())
                .body(matchesJsonSchemaInClasspath("schemas/json/order.schema.json"))
                .body("items.size()",notNullValue())
                .body("status", equalTo("CREATED"));
    }
    @Test
    @DisplayName("T7: Get /Orders All")
    void getOrderedProducts(){
        given()
                .spec(apiKeyReqSpec("/partner/orders"))
                .when()
                .get("/{id}", 5001)
                .then()
                .spec(resSpec())
                .body(matchesJsonSchemaInClasspath("schemas/json/partner_order.schema.json"))
                .body("order.total",greaterThanOrEqualTo(1))
                .body("order.items.size()", greaterThan(0) );
    }

    @Test
    @DisplayName("T8: Post an item to order from current cart")
    void orderProductFromCart(){
        var order = Map.of("paymentMethod", "Credit card", "deliverySlot", "Tomorrow 10 AM - 1 PM", "address", "UST Training Lab, Bengaluru", "shipping", 49, "discount", 0);
        given()
                .spec(reqSpec("/orders", TOKEN))
                .body(order)
                .when()
                .post()
                .then()
                .spec(orderResSpec())
                .body(matchesJsonSchemaInClasspath("schemas/json/cart-order.schema.json"))
                .body("items.size()",notNullValue())
                .body("status", equalTo("Confirmed"));
    }

    @Test
    @DisplayName("T9: check allocated status order")
    void orderAllocate(){
        var order = Map.of(
                "productId", List.of(101,106),
                "currency", "INR"
        );
        int id = given()
                .spec(reqSpec("/secure/orders", TOKEN))
                .body(order)
                .when()
                .post()
                .then()
                .spec(orderResSpec())
                .body(matchesJsonSchemaInClasspath("schemas/json/order.schema.json"))
                .extract()
                .path("id");
        given()
                .spec(alloReqSpec("/secure/orders", id ,TOKEN))
                .when()
                .post()
                .then()
                .spec(resSpec())
                .body(matchesJsonSchemaInClasspath("schemas/json/cart-order.schema.json"))
                .body("items.size()",notNullValue())
                .body("status", equalTo("ALLOCATED"));
    }

    @Test
    @DisplayName("T10: check ship status order")
    void orderShip() {

        int id =
                given()
                        .spec(reqSpec("/secure/orders", TOKEN))
                        .when()
                        .post()
                        .then()
                        .spec(orderResSpec())
                        .body(matchesJsonSchemaInClasspath("schemas/json/order.schema.json"))
                        .extract()
                        .path("id");

        given()
                .spec(alloReqSpec("/secure/orders", id, TOKEN))
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("status", equalTo("ALLOCATED"));

        given()
                .spec(shipReqSpec("/secure/orders", id, TOKEN))
                .when()
                .post()
                .then()
                .spec(resSpec())
                .body(matchesJsonSchemaInClasspath("schemas/json/cart-order.schema.json"))
                .body("items.size()", greaterThan(0))
                .body("status", equalTo("SHIPPED"));
    }

    @Test
    @DisplayName("T11: Cannot ship CREATED order")
    void ship409() {

        var order = Map.of(
                "productId", List.of(101, 106),
                "currency", "INR"
        );
        int id =
                given()
                        .spec(reqSpec("/secure/orders", TOKEN))
                        .body(order)
                        .when()
                        .post()
                        .then()
                        .spec(orderResSpec())
                        .extract()
                        .path("id");

        given()
                .spec(shipReqSpec("/secure/orders", id, TOKEN))
                .when()
                .post()
                .then()
                .spec(notAlloResSpec())
                .body("message",
                        equalTo("Cannot move order from CREATED to SHIPPED"))
                .body("currentStatus", equalTo("CREATED"))
                .body("expectedStatus", equalTo("ALLOCATED"));
    }
}
