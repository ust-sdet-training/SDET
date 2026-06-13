package tests;

import models.OrderRequest;
import org.junit.jupiter.api.Test;

import java.util.List;


import static io.restassured.RestAssured.given;

public class WrongRole403 {
    @Test
    void createOrderWithoutAuthShouldReturn403() {

        OrderRequest request = new OrderRequest(List.of(101, 102));

        given()
                .contentType("application/json")
                .header("Authorization", "Bearer invalid_token")
                .body(request)
                .when()
                .post("/orders")
                .then()
                .statusCode(403);
    }
}
