package com.ust.sdet.api.apiframework.testData;

import com.ust.sdet.api.dbframework.config.DatabaseConfig;
import com.ust.sdet.api.dbframework.model.OrderRow;
import com.ust.sdet.api.dbframework.support.DbSupport;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ust.sdet.api.apiframework.auth.apiConfig.LoginToken;
import static com.ust.sdet.api.apiframework.spec.SpecFactory.*;
import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class Testing {

    DatabaseConfig config = DatabaseConfig.fromEnvironment();
    DbSupport dbSupport = new DbSupport(config);

    private final List<Long> createdOrderIds = new ArrayList<>();

    static String accessToken;

    @BeforeAll
    static void setup() {
        accessToken = LoginToken();
    }

    @AfterEach
    void cleanCreatedRows() {

        for (Long orderId : createdOrderIds) {
            try {
                dbSupport.deleteOrder(orderId);
            } catch (Exception e) {
                System.out.println("Failed to delete Order ID: " + orderId);
            }
        }

        createdOrderIds.clear();
    }

    @Test
    @DisplayName("Missing Token")
    void missingToken() {
        given()
                .spec(notauthed())
                .when()
                .get("/{id}", 5001)
                .then()
                .spec(missTokenRes());
    }

    @Test
    @DisplayName("Invalid Token")
    void InvalidToken() {
        given()
                .spec(invalidTokenReq())
                .when()
                .get("/{id}", 5001)
                .then()
                .spec(invalidTokenRes());
    }

    @Test
    @DisplayName("No OPS(VIEWERS) BUT VALID")
    void NoOPSToken() {
        var order = Map.of("items", List.of(101, 107), "currency", "INR");

        given()
                .spec(NoOPSTokenReq())
                .body(order)
                .when()
                .post()
                .then()
                .spec(NoOPSTokenRes());
    }

    @Test
    @DisplayName("Token Expired")
    void expiredToken() {
        given()
                .spec(expiredTokenReq())
                .when()
                .get("/{id}", 5001)
                .then()
                .spec(expiredTokenRes());
    }

    @Test
    @DisplayName("FULL FLOW : LOGIN TO PLACE ORDER")

    void fullFlow() {



        var login = Map.of("email", "customer@example.com", "password", "Password@123");

        given()
                .spec(Logreq())
                .body(login)
                .when()
                .post()
                .then()
                .spec(Logres())
                .extract();


        given()
                .spec(deleteJsonReq(accessToken))
                .when()
                .delete()
                .then()
                .spec(deleteJsonRes());


        var order = Map.of("productId", 103, "quantity", 4, "size", "UK 7", "color", "Navy", "fulfilment", "Home delivery");

        given()
                .spec(addToCartreq(accessToken))
                .body(order)
                .when()
                .post()
                .then()
                .log().all()
                .spec(postJson())

                .body("id", notNullValue())
                .body("userId", notNullValue())
                .body("ownerKey", not(emptyString()))
                .body("productId", equalTo(order.get("productId")))
                .body("quantity", equalTo(order.get("quantity")))
                .body("size", equalTo(order.get("size")))
                .body("color", equalTo(order.get("color")))
                .body("fulfilment", equalTo(order.get("fulfilment")))

                .extract();


        var orders = Map.of("paymentMethod", "Credit card", "deliverySlot", "Tomorrow 9 AM - 12 PM", "address", "UST Campus, Bengaluru", "coupon", "", "shipping", 199, "discount", 0);

        Response res = given()
                .spec(placeOrder(accessToken))
                .body(orders)
                .when()
                .post()
                .then()
                .log().all()
                .spec(postJson())
                .body("id", notNullValue())
                .body("orderNumber", notNullValue())
                .body("orderNumber", notNullValue())
                .body("paymentMethod", equalTo(orders.get("paymentMethod")))
                .body("deliverySlot", equalTo(orders.get("deliverySlot")))
                .body("address", equalTo(orders.get("address")))
                .body("coupon", equalTo(orders.get("coupon")))
                .body("shipping", equalTo(orders.get("shipping")))
                .body("discount", equalTo(orders.get("discount")))
                .body(matchesJsonSchemaInClasspath("schemas/order.schemas.json"))
                .extract().response();

        int c=res.path("id");

        given()
                .spec(getauthedReq(accessToken))
                .when()
                .get("/{id}",c)
                .then()
                .log().all()
                .spec(getauthedRes())
                .body("id", notNullValue())
                .body("orderNumber", notNullValue())
                .body("orderNumber", notNullValue())
                .body("paymentMethod", equalTo(orders.get("paymentMethod")))
                .body("deliverySlot", equalTo(orders.get("deliverySlot")))
                .body("address", equalTo(orders.get("address")))
                .body("coupon", equalTo(orders.get("coupon")))
                .body("shipping", equalTo(orders.get("shipping")))
                .body("discount", equalTo(orders.get("discount")));



    }
    @Test
    @DisplayName("Checking Shipping Status + DB validation")
    void orderShip() throws SQLException {

        int id =
                given()
                        .spec(reqSpec("/secure/orders", accessToken))
                        .when()
                        .post()
                        .then()
                        .spec(postJson())
                        .extract()
                        .path("id");

        given()
                .spec(alloReqSpec("/secure/orders", id, accessToken))
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("status", equalTo("ALLOCATED"));

        given()
                .spec(shipReqSpec("/secure/orders", id, accessToken))
                .when()
                .post()
                .then()
                .spec(resSpec())
                .body("items.size()", greaterThan(0))
                .body("status", equalTo("SHIPPED"));

        // ================= DB VALIDATION =================
        OrderRow dbRow = dbSupport.findOrder((long) id);
        assertNotNull(dbRow);
        assertEquals("SHIPPED", dbRow.status());
    }

    @Test
    @DisplayName("Testing XML")
    void XML() {
        given()
                .spec(reqXml())
                .when()
                .get()
                .then()
                .spec(resXml())
                .body(matchesXsdInClasspath("schemas/products.xsd"));
    }
}