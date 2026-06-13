package services;

import io.restassured.response.Response;
import models.OrderRequest;
import models.OrderResponse;
import specs.RequestSpecs;
import support.TokenManager;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderService {

    public static long createOrder(OrderRequest request) {

        Response response =
                given()
                        .spec(RequestSpecs.apiSpec())
                        .auth()
                        .oauth2(TokenManager.getToken())
                        .body(request)
                        .when()
                        .post("/api/secure/orders")
                        .then()
                        .statusCode(201)
                        .extract()
                        .response();

        Number orderId = response.path("orderId");

        return orderId.longValue();
    }

    public OrderResponse getOrder(long orderId) {

        return given()
                .spec(RequestSpecs.apiSpec())
                .auth()
                .oauth2(TokenManager.getToken())
                .when()
                .get("/api/secure/orders/{id}", orderId)
                .then()
                .statusCode(200)
                .extract()
                .as(OrderResponse.class);
    }

    public static OrderResponse allocateOrder(long orderId) {

        return given()
                .spec(RequestSpecs.apiSpec())
                .auth()
                .oauth2(TokenManager.getToken())
                .when()
                .post("/api/secure/orders/{id}/allocate", orderId)
                .then()
                .statusCode(200)
                .extract()
                .as(OrderResponse.class);
    }

    public static OrderResponse shipOrder(long orderId) {

        return given()
                .spec(RequestSpecs.apiSpec())
                .auth()
                .oauth2(TokenManager.getToken())
                .when()
                .post("/api/secure/orders/{id}/ship", orderId)
                .then()
                .statusCode(200)
                .extract()
                .as(OrderResponse.class);
    }

    public Response createSecureOrder() {

        Map<String, Object> payload =
                Map.of(
                        "items", List.of(101, 107),
                        "currency", "INR"
                );

        return given()
                .spec(RequestSpecs.apiSpec())
                .auth()
                .oauth2(TokenManager.getToken())
                .body(payload)
                .when()
                .post("/api/secure/orders")
                .then()
                .extract()
                .response();
    }
}
