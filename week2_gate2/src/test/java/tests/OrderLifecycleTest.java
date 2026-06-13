package tests;

import base.BaseTest;
import models.OrderRequest;
import models.OrderResponse;
import org.junit.jupiter.api.Test;
import services.OrderService;
import support.TokenManager;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;

public class OrderLifecycleTest extends BaseTest {

    @Test
    void orderLifecycleShouldSucceed() {

        String token = TokenManager.opsToken();

        OrderRequest request =
                new OrderRequest(
                        List.of(101, 102)
                );

        OrderResponse createdOrder =
                OrderService.createSecureOrder(
                                token,
                                request
                        )
                        .then()
                        .statusCode(201)
                        .body(matchesJsonSchemaInClasspath(
                                "schemas/create-order-schema.json"))
                        .extract()
                        .as(OrderResponse.class);

        long orderId = createdOrder.getId();

        assertNotNull(createdOrder.getOrderNumber());
        assertEquals("CREATED", createdOrder.getStatus());

        OrderResponse allocatedOrder =
                OrderService.allocateOrder(
                                token,
                                (int) orderId
                        )
                        .then()
                        .statusCode(200)
                        .body(matchesJsonSchemaInClasspath(
                                "schemas/allocate-schema.json"))
                        .extract()
                        .as(OrderResponse.class);

        assertEquals(
                "ALLOCATED",
                allocatedOrder.getStatus()
        );

        OrderResponse shippedOrder =
                OrderService.shipOrder(
                                token,
                                (int) orderId
                        )
                        .then()
                        .statusCode(200)
                        .body(matchesJsonSchemaInClasspath(
                                "schemas/ship-schema.json"))
                        .extract()
                        .as(OrderResponse.class);

        assertEquals(
                "SHIPPED",
                shippedOrder.getStatus()
        );

        OrderResponse fetchedOrder =
                OrderService.getSecureOrder(
                                token,
                                (int) orderId
                        )
                        .then()
                        .statusCode(200)
                        .body(matchesJsonSchemaInClasspath(
                                "schemas/order-schema.json"))
                        .extract()
                        .as(OrderResponse.class);

        assertEquals(
                orderId,
                fetchedOrder.getId()
        );

        OrderService.deleteOrder(
                        token,
                        (int) orderId
                )
                .then()
                .statusCode(204);
    }
}