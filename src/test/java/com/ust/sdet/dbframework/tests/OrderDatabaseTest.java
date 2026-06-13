package com.ust.sdet.dbframework.tests;

import com.ust.sdet.auth.Auth;
import com.ust.sdet.base.BaseDatabaseTest;
import com.ust.sdet.dbframework.model.OrderRow;
import com.ust.sdet.specs.RequestSpecs;
import com.ust.sdet.specs.ResponseSpecs;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class OrderDatabaseTest extends BaseDatabaseTest {
    @Test
    @DisplayName("Create secure order and validate database")
    void createOrderAndValidateDatabase() throws Exception {
        Response response = given()
                .spec(RequestSpecs.authenticatedSpec(Auth.oauthToken()))
                        .body("""
                                {
                                  "items":[101,107]
                                }
                                """)
                        .when()
                        .post("/api/secure/orders")
                        .then()
                        .spec(ResponseSpecs.created201Spec())
                        .body("id", notNullValue(), "status", equalTo("CREATED"), "total", greaterThan(0))
                        .extract()
                        .response();

        int orderId = response.path("id");
        assertTrue(orderId > 0);
        OrderRow order = db.findOrder(orderId);
        assertNotNull(order);
        assertEquals(orderId, order.getId());
        assertEquals("CREATED", order.getStatus());
        assertTrue(order.getTotal().compareTo(BigDecimal.ZERO) > 0);
        assertNotNull(order.getCreatedAt());
    }
}