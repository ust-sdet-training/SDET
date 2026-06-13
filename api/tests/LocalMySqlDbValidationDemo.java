package com.ust.sdet.api.tests;

import com.ust.sdet.api.dbframework.config.DatabaseConfig;
import com.ust.sdet.api.dbframework.model.OrderRow;
import com.ust.sdet.api.dbframework.support.DbSupport;
import com.ust.sdet.api.specs.SpecFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class LocalMySqlDbValidationDemo {

    private static DbSupport database;
    private static String viewerToken;

    @BeforeAll
    static void setup() {
        database = new DbSupport(DatabaseConfig.fromEnvironment());
    }

    @Test
    @DisplayName("Database connectivity")
    void localMySqlIsReachable() throws Exception {

        assertTrue(database.isReachable());
    }

    @Test
    @DisplayName("Find order by id")
    void findOrder() throws Exception {
        long orderId = 5001;
        OrderRow row = database.findOrder(orderId);
        assertNotNull(row);
        System.out.println(row);
    }

    @Test
    @DisplayName("Validate persisted order details")
    void validatePersistedOrderDetails() throws Exception {
        OrderRow row = database.findOrder(5001);
        assertNotNull(row);
        assertEquals("ORD-5001", row.orderNumber());
        assertEquals("Confirmed", row.status());
        assertEquals("svc-retail-ops", row.userId());
        assertEquals(
                0,
                row.total().compareTo(
                        new BigDecimal("9047.00")
                )
        );
    }
    @Test
    @DisplayName("Created order is persisted and correct")
    void createdOrderIsPersisted() throws Exception {
        viewerToken =
                given()
                        .spec(SpecFactory.oauthSpec())
                        .auth()
                        .preemptive()
                        .basic("retail-ops-client", "2a2729b27b47fe27b6412403d886ef4781bbff36b0e2b58e")
                        .when()
                        .post("/api/oauth/token")
                        .then()
                        .log().all()
                        .spec(SpecFactory.ok200())
                        .extract()
                        .path("access_token");

        Response response =
                given()
                        .spec(SpecFactory.authed(viewerToken))
                        .body(Map.of(
                                "items", List.of(101, 107),
                                "currency", "INR"
                        ))
                        .when()
                        .post("/api/secure/orders")
                        .then()
                        .statusCode(201)
                        .extract()
                        .response();
        System.out.println(response.asString());
        long id = response.jsonPath().getLong("orderId");
        OrderRow row = database.findOrder(id);
        assertNotNull(row, "Order must be persisted");
        assertEquals(id, row.id());
        assertEquals("CREATED", row.status());
        assertEquals(
                0,
                row.total().compareTo(
                        response.jsonPath()
                                .getObject("total", BigDecimal.class)
                )
        );
    }
    @Test
    @DisplayName("Unknown order returns null")
    void unknownOrderReturnsNull() throws Exception {
        OrderRow row = database.findOrder(999999);
        assertNull(row);
    }
    @Test
    @DisplayName("Created order contains audit information")
    void createdOrderContainsAuditFields() throws Exception {
        OrderRow row = database.findOrder(5001);
        assertNotNull(row);
        assertNotNull(row.userId());
        assertNotNull(row.orderNumber());
        assertTrue(row.total().compareTo(BigDecimal.ZERO) > 0);
    }
}