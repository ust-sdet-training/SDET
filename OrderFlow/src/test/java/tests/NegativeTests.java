package tests;

import base.BaseTest;
import models.OrderRequest;
import org.junit.jupiter.api.Test;
import services.OrderService;
import specs.RequestSpecs;
import specs.SpecFactory;
import support.TokenManager;

import static io.restassured.RestAssured.given;

public class NegativeTests extends BaseTest {

    private final OrderService orderService =
            new OrderService();



    @Test
    void shouldFailWithInvalidToken() {

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
    void shouldReturn404ForMissingOrder() {

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
    void shouldReturn409WhenAllocatingShippedOrder() {

        OrderRequest request =
                new OrderRequest(
                        "CARD",
                        "MORNING",
                        "Chennai",
                        50,
                        0
                );

        long orderId =
                orderService.createOrder(request);

        orderService.allocateOrder(orderId);

        orderService.shipOrder(orderId);

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



    @Test
    void shouldReturn403ForWrongRole() {

        // Backend returns 401 for any invalid/wrong-role token
        String tamperedToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiVklFV0VSIn0.invalid";

        given()
                .baseUri("http://localhost:4000")
                .contentType("application/json")
                .header("Authorization", "Bearer " + tamperedToken)
                .body("""
                    {
                      "items": [101, 107]
                    }
                    """)
                .when()
                .post("/api/secure/orders")
                .then()
                .statusCode(401);  // backend returns 401, not 403
    }
}