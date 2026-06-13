package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import support.TokenManager;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class OrderLifecycleTest extends BaseTest {

    @Test
    void shouldShipOrder() {

        String token = TokenManager.getToken();

        //  Create order
        Response createResponse = given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .body(Map.of(
                        "items", new int[]{101, 107},
                        "currency", "INR"
                ))
                .when()
                .post("/api/secure/orders")
                .then()
                .statusCode(201)
                .extract()
                .response();

        int orderId = createResponse.path("id");

        System.out.println("Created Order ID = " + orderId);

        //  Allocate order
        given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/api/secure/orders/" + orderId + "/allocate")
                .then()
                .statusCode(200)
                .body("status", equalTo("ALLOCATED"));

        System.out.println("Order Allocated");

        //  Ship order
        given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/api/secure/orders/" + orderId + "/ship")
                .then()
                .statusCode(200)
                .body("status", equalTo("SHIPPED"));

        System.out.println("Order Shipped");
    }
}