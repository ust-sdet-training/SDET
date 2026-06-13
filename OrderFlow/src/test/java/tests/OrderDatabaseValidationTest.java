package tests;

import base.BaseTest;
import database.DatabaseUtils;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import support.TokenManager;

import java.util.Map;


import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderDatabaseValidationTest
        extends BaseTest {

    @Test
    void validateOrderInDatabase() {

        String token = TokenManager.getToken();

        Response response = given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .body(Map.of(
                        "items",
                        new int[]{101, 107},
                        "currency",
                        "INR"
                ))
                .when()
                .post("/api/secure/orders")
                .then()
                .statusCode(201)
                .extract()
                .response();

        int orderId = response.path("id");

        String actualStatus = DatabaseUtils.getOrderStatus(orderId);
        System.out.println("DB Status = " + actualStatus);
        assertEquals("CREATED", actualStatus);
    }
}