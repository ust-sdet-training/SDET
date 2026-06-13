package com.ust.sdet.api.tests.life_cycle.LifeCycle;

import com.ust.sdet.api.API_FrameWork.Factory.SpecFactory;
import com.ust.sdet.api.API_FrameWork.config.ApiConfig;
import com.ust.sdet.api.dbframework.assertions.Database_Assertions;
import com.ust.sdet.api.dbframework.config.DatabaseConfig;
import com.ust.sdet.api.dbframework.model.OrderRow;
import com.ust.sdet.api.dbframework.repository.OrderRepository;
import com.ust.sdet.api.dbframework.support.DbSupport;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;

import java.util.ArrayList;
import java.util.List;

public class Ops_OrderLifeCycle {

    private final List<Long> createdOrderIds = new ArrayList<>();

    private final OrderLifeCycleHelper lifecycle = new OrderLifeCycleHelper();

    private static final String BASE_URL = ApiConfig.BASE_URL;

    DatabaseConfig config = DatabaseConfig.fromEnvironment();
    DbSupport dbSupport = new DbSupport(config);
    OrderRepository repository = new OrderRepository(dbSupport);

    @BeforeAll
    static void setup() {

        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";

    }
    @AfterEach
    void cleanCreatedRows() throws Exception {

        for (Long orderId : createdOrderIds) {

            int deletedRows =
                    repository.deleteOrder(orderId);

            System.out.println(
                    "Deleted Order ID: "
                            + orderId
                            + " | Rows Deleted: "
                            + deletedRows
            );
        }

        createdOrderIds.clear();
    }

    @Test
    @DisplayName("Local H1: MySQL is reachable through JDBC")
    void locallySqlIsReachable() throws Exception {
        assertTrue(dbSupport.isReachable());
    }
    @Test
    @DisplayName("Order Lifecycle: Create -> Allocate -> Ship -> Fetch")
    void orderLifeCycle() throws SQLException {


        var item = Map.of(
                "productId", 103,
                "quantity", 1,
                "size", "Standard",
                "color", "Black",
                "fulfilment", "Home delivery"
        );
        var order = Map.of(
                "paymentMethod", "Credit card",
                "deliverySlot", "Tomorrow 9 AM - 12 PM",
                "address", "UST Campus, Bengaluru"
        );

        given()
                .spec(SpecFactory.opsRead())

                .when()
                .get(SpecFactory.productsPath())

                .then()
                .spec(SpecFactory.ok200())
                .body(matchesJsonSchemaInClasspath(SpecFactory.PRODUCT_LIST_SCHEMA))
                .body("items.size()", greaterThan(0))
                .body("items[0].name", equalTo("Running Shoes"))
                .body("items[0].stock", equalTo(18))
                .body("items[0].category", equalTo("Footwear"));

        given()
                .spec(SpecFactory.opsRead())

                .when()
                .delete(SpecFactory.cartPath())

                .then()
                .spec(SpecFactory.noContent204());

        given()
                .spec(SpecFactory.opsWrite())
                .body(item)

                .when()
                .post(SpecFactory.cartItemsPath())

                .then()
                .statusCode(anyOf(equalTo(200), equalTo(201)));


        Response response =
                given()
                        .spec(SpecFactory.opsWrite())
                        .body(order)

                        .when()
                        .post(SpecFactory.secureOrdersPath())

                        .then()
                        .log().all()
                        .spec(SpecFactory.created201())
                        .body(matchesJsonSchemaInClasspath(SpecFactory.OPS_ORDER_SCHEMA))
                        .extract()
                        .response();

        assertEquals("CREATED", response.jsonPath().getString("status"));

        assertTrue(response.jsonPath().getLong("id") > 0);

        assertEquals(response.jsonPath().getLong("id"), response.jsonPath().getLong("orderId"));

        assertTrue(response.jsonPath().getString("orderNumber").startsWith("ORD-"));

        assertEquals(1, response.jsonPath().getList("items").size());

        assertEquals(101, response.jsonPath().getInt("items[0].productId"));

        long orderId = response.jsonPath().getLong("id");

        createdOrderIds.add(orderId);

        OrderRow dbOrder = repository.findOrder(orderId);

        Database_Assertions.assertOrderExists(dbOrder);

        Database_Assertions.assertOrderStatus(
                dbOrder,
                response.jsonPath().getString("status")
        );

        Database_Assertions.assertOrderNumber(
                dbOrder,
                response.jsonPath().getString("orderNumber")
        );

        Response allocateResponse =
                given()
                        .spec(SpecFactory.opsWrite())

                        .when()
                        .post(
                                SpecFactory.allocateOrderPath(orderId)
                        )

                        .then()
                        .log().all()
                        .body(matchesJsonSchemaInClasspath(SpecFactory.OPS_ORDER_SCHEMA))
                        .spec(SpecFactory.ok200())
                        .extract()
                        .response();

        assertEquals("ALLOCATED", allocateResponse.jsonPath().getString("status"));

        assertTrue(response.jsonPath().getLong("id") > 0);

        assertEquals(response.jsonPath().getLong("id"), response.jsonPath().getLong("orderId"));

        assertTrue(response.jsonPath().getString("orderNumber").startsWith("ORD-"));

        assertEquals(1, response.jsonPath().getList("items").size());

        assertEquals(101, response.jsonPath().getInt("items[0].productId"));

        OrderRow allocatedOrder = repository.findOrder(orderId);

        Database_Assertions.assertOrderStatus(
                allocatedOrder,
                "ALLOCATED"
        );



        Response shipResponse =
                given()
                        .spec(SpecFactory.opsWrite())

                        .when()
                        .post(SpecFactory.shipOrderPath(orderId))

                        .then()
                        .log().all()
                        .body(matchesJsonSchemaInClasspath(SpecFactory.OPS_ORDER_SCHEMA))
                        .spec(SpecFactory.ok200())
                        .extract()
                        .response();

        assertEquals("SHIPPED", shipResponse.jsonPath().getString("status"));

        assertTrue(response.jsonPath().getLong("id") > 0);

        assertEquals(response.jsonPath().getLong("id"), response.jsonPath().getLong("orderId"));

        assertTrue(response.jsonPath().getString("orderNumber").startsWith("ORD-"));

        assertEquals(1, response.jsonPath().getList("items").size());

        assertEquals(101, response.jsonPath().getInt("items[0].productId"));

        OrderRow shippedOrder = repository.findOrder(orderId);

        Database_Assertions.assertOrderStatus(
                shippedOrder,
                "SHIPPED"
        );


        Response fetchResponse =
                given()
                        .spec(SpecFactory.opsRead())

                        .when()
                        .get(SpecFactory.secureOrderById(orderId))

                        .then()
                        .body(matchesJsonSchemaInClasspath(SpecFactory.OPS_ORDER_SCHEMA))
                        .spec(SpecFactory.ok200())
                        .extract()
                        .response();

        assertEquals("SHIPPED", fetchResponse.jsonPath().getString("status"));

        assertTrue(response.jsonPath().getLong("id") > 0);

        assertEquals(response.jsonPath().getLong("id"), response.jsonPath().getLong("orderId"));

        assertTrue(response.jsonPath().getString("orderNumber").startsWith("ORD-"));

        assertEquals(1, response.jsonPath().getList("items").size());

        assertEquals(101, response.jsonPath().getInt("items[0].productId"));

    }

    @Test
    @DisplayName("Complete Order Lifecycle Workflow using OOPS")
    void completeOrderLifecycleWorkflow_usingOOPS() throws SQLException {

        long orderId = lifecycle.createOrder();

        Response allocated = lifecycle.allocateOrder(orderId);

        assertEquals(
                "ALLOCATED",
                allocated.jsonPath().getString("status")
        );

        Response shipped = lifecycle.shipOrder(orderId);

        assertEquals(
                "SHIPPED",
                shipped.jsonPath().getString("status")
        );

        Response fetched = lifecycle.fetchOrder(orderId);

        assertEquals(
                "SHIPPED",
                fetched.jsonPath().getString("status")
        );
    }

}