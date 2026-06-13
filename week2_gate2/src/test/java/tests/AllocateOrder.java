package tests;

import io.restassured.response.Response;
import models.OrderRequest;
import models.OrderResponse;
import org.junit.jupiter.api.Test;
import services.OrderService;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static specs.SpecFactory.authSpec;

public class AllocateOrder {

    @Test
    void orderLifecycleShouldSucceed() {

        OrderService orderService = new OrderService();

        OrderRequest request =
                new OrderRequest(
                        List.of(101, 102)
                );

        OrderResponse createdOrder =
                orderService.createOrder(request)
                        .then()
                        .statusCode(201)
                        .extract()
                        .as(OrderResponse.class);

        long orderId = createdOrder.getId();

        System.out.println("Created Order ID = " + orderId);
        System.out.println("Status = " + createdOrder.getStatus());

        assertTrue(orderId > 0);

        // Allocate Order
        OrderResponse allocatedOrder =
                orderService.allocateOrder(orderId)
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(OrderResponse.class);

        System.out.println("Allocated Status = "
                + allocatedOrder.getStatus());

        assertNotNull(allocatedOrder.getStatus());

        // Ship Order
        OrderResponse shippedOrder =
                orderService.shipOrder(orderId)
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(OrderResponse.class);

        System.out.println("Shipped Status = "
                + shippedOrder.getStatus());

        assertNotNull(shippedOrder.getStatus());

        // Fetch Order
        OrderResponse fetchedOrder =
                orderService.fetchOrder(orderId)
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(OrderResponse.class);

        System.out.println("Fetched Order ID = "
                + fetchedOrder.getId());

        assertEquals(
                orderId,
                fetchedOrder.getId()
        );
    }
}