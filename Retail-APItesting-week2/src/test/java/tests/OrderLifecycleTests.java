package tests;

import base.BaseTest;
import io.restassured.RestAssured;
import models.OrderRow;
import models.request.CreateOrderRequest;
import models.response.OrderResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.RequestSpecs;
import specs.ResponseSpecs;
import utils.TokenManager;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;

public class OrderLifecycleTests extends BaseTest {

    @Test
    @DisplayName("Verify complete order lifecycle")
    void verifyCompleteOrderLifecycle() throws Exception {

        String token =
                TokenManager.getOpsAccessToken();

        CreateOrderRequest request =
                new CreateOrderRequest(
                        List.of(101, 107),
                        "INR"
                );


        OrderResponse createdOrder =

                given()
                        .spec(
                                specs.RequestSpecs
                                        .opsAuthSpec(token))
                        .body(request)

                        .when()
                        .post("/api/secure/orders")

                        .then()
                        .statusCode(201)
                        .body(
                                matchesJsonSchemaInClasspath(
                                        "schemas/order-schema.json"))
                        .extract()
                        .as(OrderResponse.class);

        assertNotNull(createdOrder);

        assertTrue(createdOrder.getId() > 0);

        assertNotNull(
                createdOrder.getOrderNumber());

        assertEquals(
                "CREATED",
                createdOrder.getStatus());



        OrderRow createdDbOrder =
                dbSupport.findOrder(
                        createdOrder.getId());

        assertNotNull(createdDbOrder);

        assertEquals(
                createdOrder.getOrderNumber(),
                createdDbOrder.getOrderNumber());

        assertEquals(
                "CREATED",
                createdDbOrder.getStatus());



        OrderResponse allocatedOrder =

                given()
                        .spec(
                                specs.RequestSpecs
                                        .opsAuthSpec(token))

                        .when()
                        .post(
                                "/api/secure/orders/"
                                        + createdOrder.getId()
                                        + "/allocate")

                        .then()
                        .statusCode(200)
                        .body(
                                matchesJsonSchemaInClasspath(
                                        "schemas/order-schema.json"))
                        .extract()
                        .as(OrderResponse.class);

        assertEquals(
                "ALLOCATED",
                allocatedOrder.getStatus());

        assertEquals(
                "ALLOCATED",
                dbSupport.getOrderStatus(
                        createdOrder.getId()));



        OrderResponse shippedOrder =

                given()
                        .spec(
                                specs.RequestSpecs
                                        .opsAuthSpec(token))

                        .when()
                        .post(
                                "/api/secure/orders/"
                                        + createdOrder.getId()
                                        + "/ship")

                        .then()
                        .statusCode(200)
                        .body(
                                matchesJsonSchemaInClasspath(
                                        "schemas/order-schema.json"))
                        .extract()
                        .as(OrderResponse.class);

        assertEquals(
                "SHIPPED",
                shippedOrder.getStatus());

        assertEquals(
                "SHIPPED",
                dbSupport.getOrderStatus(
                        createdOrder.getId()));

        OrderResponse fetchedOrder =

                given()
                        .spec(
                                specs.RequestSpecs
                                        .opsAuthSpec(token))

                        .when()
                        .get(
                                "/api/secure/orders/"
                                        + createdOrder.getId())

                        .then()
                        .statusCode(200)
                        .body(
                                matchesJsonSchemaInClasspath(
                                        "schemas/order-schema.json"))
                        .extract()
                        .as(OrderResponse.class);

        OrderRow dbOrder =
                dbSupport.findOrder(
                        createdOrder.getId());

        assertNotNull(dbOrder);



        assertEquals(
                dbOrder.getOrderNumber(),
                fetchedOrder.getOrderNumber());

        assertEquals(
                dbOrder.getStatus(),
                fetchedOrder.getStatus());

        assertEquals(
                createdOrder.getId(),
                fetchedOrder.getId());


        assertTrue(
                dbSupport.cleanupOrder(
                        createdOrder.getId()));
    }
    @Test
    void verifyXmlSchemaValidation() {

        String xml = """
            <order>
                <id>1</id>
                <status>CREATED</status>
            </order>
            """;

        RestAssured
                .given()
                .body(xml)
                .then()
                .body(matchesXsdInClasspath(
                        "schemas/order.xsd"));
    }
    @Test
    @DisplayName("Verify allocating already allocated order returns 409")
    void verifyConflictOnReAllocation() {

        String token =
                TokenManager.getOpsAccessToken();

        CreateOrderRequest request =
                new CreateOrderRequest(
                        List.of(101, 107),
                        "INR"
                );



        long orderId =

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

                        .extract()
                        .path("id");


        given()
                .spec(
                        RequestSpecs.opsAuthSpec(
                                token))

                .when()
                .post(
                        RequestSpecs.allocateOrder(
                                orderId))

                .then()
                .statusCode(200);


        given()
                .spec(
                        RequestSpecs.opsAuthSpec(
                                token))

                .when()
                .post(
                        RequestSpecs.allocateOrder(
                                orderId))

                .then()
                .spec(
                        ResponseSpecs.conflictSpec);
    }

}