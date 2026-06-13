package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static specs.SpecFactory.authSpec;

public class CreateOrder {
    @Test
    void createOrder() {

        Response cartResponse =
                given()
                        .spec(authSpec())
                        .body(Map.of(
                                "productId", 101,
                                "quantity", 2
                        ))
                        .when()
                        .post("/api/cart/items")
                        .then()
                        .statusCode(201)
                        .extract()
                        .response();

        assertNotNull(cartResponse);

        Response orderResponse =
                given()
                        .spec(authSpec())
                        .body(Map.of(
                                "paymentMethod", "Cash on delivery",
                                "deliverySlot", "Tomorrow 9 AM-12 PM",
                                "address", "UST Campus, Bengaluru"
                        ))
                        .when()
                        .post("/api/orders")
                        .then()
                        .statusCode(201)
                        .extract()
                        .response();

        assertNotNull(orderResponse);

        Integer orderId = orderResponse.jsonPath().getInt("id");
        assertNotNull(orderId);
        assertTrue(orderId > 0);

        String paymentMethod =
                orderResponse.jsonPath().getString("paymentMethod");
        assertEquals("Cash on delivery", paymentMethod);

        String address =
                orderResponse.jsonPath().getString("address");
        assertEquals("UST Campus, Bengaluru", address);
    }

}
