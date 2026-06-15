package tests;

import base.BaseTest;
import models.OrderRow;
import models.request.CreateOrderRequest;
import models.response.OrderResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.RequestSpecs;
import specs.ResponseSpecs;
import utils.TokenManager;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;

public class OrderLifecycleTests extends BaseTest {

    @Test
    @DisplayName("Verify order creation and database persistence")
    void verifyOrderCreation() throws Exception {

        String token =
                TokenManager.getOpsAccessToken();

        CreateOrderRequest request =
                new CreateOrderRequest(
                        List.of(101, 107),
                        "INR"
                );

        OrderResponse order =

                given()
                        .spec(
                                RequestSpecs.opsAuthSpec(
                                        token))
                        .body(request)

                        .when()
                        .post(
                                RequestSpecs.createOrderPath())

                        .then()
                        .spec(
                                ResponseSpecs.orderCreatedSpec)
                        .body(
                                matchesJsonSchemaInClasspath(
                                        "schemas/order-schema.json"))
                        .extract()
                        .as(OrderResponse.class);

        assertNotNull(order);

        assertTrue(
                order.getId() > 0);

        assertNotNull(
                order.getOrderNumber());

        assertEquals(
                "CREATED",
                order.getStatus());

        OrderRow dbOrder =
                dbSupport.findOrder(
                        order.getId());

        assertNotNull(dbOrder);

        assertEquals(
                order.getOrderNumber(),
                dbOrder.getOrderNumber());

        assertEquals(
                order.getStatus(),
                dbOrder.getStatus());
    }

    @Test
    @DisplayName("Verify created order can be fetched")
    void verifyFetchOrderById() {

        String token =
                TokenManager.getOpsAccessToken();

        CreateOrderRequest request =
                new CreateOrderRequest(
                        List.of(101, 107),
                        "INR"
                );

        int orderId =

                given()
                        .spec(
                                RequestSpecs.opsAuthSpec(
                                        token))
                        .body(request)

                        .when()
                        .post(
                                RequestSpecs.createOrderPath())

                        .then()
                        .statusCode(201)

                        .extract()
                        .path("id");

        OrderResponse fetchedOrder =

                given()
                        .spec(
                                RequestSpecs.opsAuthSpec(
                                        token))

                        .when()
                        .get(
                                RequestSpecs.orderById(
                                        orderId))

                        .then()
                        .statusCode(200)
                        .body(
                                matchesJsonSchemaInClasspath(
                                        "schemas/order-schema.json"))
                        .extract()
                        .as(OrderResponse.class);

        assertEquals(
                orderId,
                fetchedOrder.getId());
    }

    @Test
    @DisplayName("Verify database reconciliation")
    void verifyDatabaseReconciliation()
            throws Exception {

        String token =
                TokenManager.getOpsAccessToken();

        CreateOrderRequest request =
                new CreateOrderRequest(
                        List.of(101),
                        "INR"
                );

        OrderResponse order =

                given()
                        .spec(
                                RequestSpecs.opsAuthSpec(
                                        token))
                        .body(request)

                        .when()
                        .post(
                                RequestSpecs.createOrderPath())

                        .then()
                        .statusCode(201)

                        .extract()
                        .as(OrderResponse.class);

        assertTrue(
                dbSupport.reconcileOrder(
                        order.getId(),
                        order.getOrderNumber(),
                        order.getStatus()));
    }

    @Test
    @DisplayName("Verify order exists in database")
    void verifyOrderPersistence()
            throws Exception {

        String token =
                TokenManager.getOpsAccessToken();

        CreateOrderRequest request =
                new CreateOrderRequest(
                        List.of(101),
                        "INR"
                );

        int orderId =

                given()
                        .spec(
                                RequestSpecs.opsAuthSpec(
                                        token))
                        .body(request)

                        .when()
                        .post(
                                RequestSpecs.createOrderPath())

                        .then()
                        .statusCode(201)

                        .extract()
                        .path("id");

        assertTrue(
                dbSupport.verifyOrderPersisted(
                        orderId));
    }
}