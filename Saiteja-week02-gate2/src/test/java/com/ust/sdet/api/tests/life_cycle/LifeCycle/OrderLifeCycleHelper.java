package com.ust.sdet.api.tests.life_cycle.LifeCycle;

import com.ust.sdet.api.API_FrameWork.Factory.SpecFactory;
import com.ust.sdet.api.dbframework.assertions.Database_Assertions;
import com.ust.sdet.api.dbframework.config.DatabaseConfig;
import com.ust.sdet.api.dbframework.model.OrderRow;
import com.ust.sdet.api.dbframework.repository.OrderRepository;
import com.ust.sdet.api.dbframework.support.DbSupport;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderLifeCycleHelper {
    private final List<Long> createdOrderIds = new ArrayList<>();

    DatabaseConfig config = DatabaseConfig.fromEnvironment();
    DbSupport dbSupport = new DbSupport(config);
    OrderRepository repository = new OrderRepository(dbSupport);

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

    public long createOrder() throws SQLException {

        Response response =
                given()
                        .spec(SpecFactory.opsWrite())
                        .body(Map.of(
                                "items", new int[]{101},
                                "currency", "INR"
                        ))

                        .when()
                        .post(SpecFactory.secureOrdersPath())

                        .then()
                        .body(matchesJsonSchemaInClasspath(SpecFactory.OPS_ORDER_SCHEMA))
                        .spec(SpecFactory.created201())
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

        return response.jsonPath().getLong("id");
    }

    public Response allocateOrder(long orderId) throws SQLException {

        Response allocateResponse =  given()
                .spec(SpecFactory.opsWrite())

                .when()
                .post(SpecFactory.allocateOrderPath(orderId))

                .then()
                .spec(SpecFactory.ok200())
                .extract()
                .response();

        assertEquals("ALLOCATED", allocateResponse.jsonPath().getString("status"));

        assertTrue(allocateResponse.jsonPath().getLong("id") > 0);

        assertEquals(allocateResponse.jsonPath().getLong("id"), allocateResponse.jsonPath().getLong("orderId"));

        assertTrue(allocateResponse.jsonPath().getString("orderNumber").startsWith("ORD-"));

        assertEquals(1, allocateResponse.jsonPath().getList("items").size());

        assertEquals(101, allocateResponse.jsonPath().getInt("items[0].productId"));

        OrderRow allocatedOrder = repository.findOrder(orderId);

        Database_Assertions.assertOrderStatus(
                allocatedOrder,
                "ALLOCATED"
        );

        Database_Assertions.assertOrderNumber(
                allocatedOrder,
                allocateResponse.jsonPath().getString("orderNumber")
        );

        return allocateResponse;
    }

    public Response shipOrder(long orderId) throws SQLException {

        Response shipResponse =  given()
                .spec(SpecFactory.opsWrite())

                .when()
                .post(SpecFactory.shipOrderPath(orderId))

                .then()
                .spec(SpecFactory.ok200())
                .extract()
                .response();

        assertEquals("SHIPPED", shipResponse.jsonPath().getString("status"));

        assertTrue(shipResponse.jsonPath().getLong("id") > 0);

        assertEquals(shipResponse.jsonPath().getLong("id"), shipResponse.jsonPath().getLong("orderId"));

        assertTrue(shipResponse.jsonPath().getString("orderNumber").startsWith("ORD-"));

        assertEquals(1, shipResponse.jsonPath().getList("items").size());

        assertEquals(101, shipResponse.jsonPath().getInt("items[0].productId"));

        OrderRow shippedOrder = repository.findOrder(orderId);

        Database_Assertions.assertOrderStatus(
                shippedOrder,
                "SHIPPED"
        );
        Database_Assertions.assertOrderNumber(
                shippedOrder,
                shipResponse.jsonPath().getString("orderNumber")
        );
        return shipResponse;
    }

    public Response fetchOrder(long orderId) {

        Response fetchResponse =  given()
                .spec(SpecFactory.opsRead())

                .when()
                .get(SpecFactory.secureOrderById(orderId))

                .then()
                .spec(SpecFactory.ok200())
                .extract()
                .response();

        assertEquals("SHIPPED", fetchResponse.jsonPath().getString("status"));

        assertTrue(fetchResponse.jsonPath().getLong("id") > 0);

        assertEquals(fetchResponse.jsonPath().getLong("id"), fetchResponse.jsonPath().getLong("orderId"));

        assertTrue(fetchResponse.jsonPath().getString("orderNumber").startsWith("ORD-"));

        assertEquals(1, fetchResponse.jsonPath().getList("items").size());

        assertEquals(101, fetchResponse.jsonPath().getInt("items[0].productId"));

        return fetchResponse;
    }
}