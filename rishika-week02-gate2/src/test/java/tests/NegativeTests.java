package tests;

import base.BaseTest;
import models.OrderRequest;
import org.junit.jupiter.api.Test;
import services.OrderService;
import specs.RequestSpecs;
import support.TokenManager;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class NegativeTests extends BaseTest {

    @Test
    void failWithInvalidToken() {

        given()
                .spec(requestSpec)
                .header(
                        "Authorization",
                        "Bearer invalid-token"
                )
                .when()
                .get("/api/secure/orders/5001")
                .then()
                .statusCode(401);
    }


    @Test
    void return404ForMissingOrder() {

        given()
                .spec(RequestSpecs.apiSpec())
                .auth()
                .oauth2(TokenManager.getToken())
                .when()
                .get("/api/secure/orders/999999")
                .then()
                .statusCode(404);
    }

    @Test
    void return409WhenAllocatingShippedOrder() {

        OrderRequest request =
                new OrderRequest(
                        "CARD",
                        "MORNING",
                        "Chennai",
                        50,
                        0
                );

        long orderId =
                OrderService.createOrder(request);

        OrderService.allocateOrder(orderId);

        OrderService.shipOrder(orderId);

        given()
                .spec(RequestSpecs.apiSpec())
                .auth()
                .oauth2(TokenManager.getToken())
                .when()
                .post(
                        "/api/secure/orders/{id}/allocate",
                        orderId
                )
                .then()
                .statusCode(409);
    }
}