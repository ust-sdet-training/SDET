package com.ust.sdet.api.config;

import com.ust.sdet.api.model.OrderRow;
import com.ust.sdet.api.support.DbSupport;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ust.sdet.api.specification.SpecFactory.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MySqlDbValidation {
    private static final List<Long> createdOrderIds = new ArrayList<>();
    private static DbSupport database;

    @BeforeAll
    static void setup() {
        database = new DbSupport(DatabaseConfig.fromEnvironment());
    }

    @AfterEach
    void cleanCreatedRows() throws Exception{
        for( long orderId : createdOrderIds){
            database.deleteOrder(orderId);
        }
        createdOrderIds.clear();
    }

    @Test
    @DisplayName("Local M1:MySql is reachable through JDBC")
    void localMySqlIsReachable() throws Exception {
        assertTrue(database.isReachable());
    }

    @Test
    @DisplayName("Persisted")
    void createdOrder_isPersisted() throws  Exception{

        var newOrder = Map.of(
                "items", List.of(101,106),
                "currency", "INR"
        );

        Response c =
                given()
                        .spec(authedOrders("/secure/orders",token()))
                        .body(newOrder)
                        .when()
                        .post("")
                        .then()
                        .statusCode(201)
                        .extract()
                        .response();
        long id = c.jsonPath().getLong("orderId");

        OrderRow row = DbSupport.findOrder(id);
        assertNotNull(row, "order must be persisted");
        assertEquals("CREATED", row.status());
        assertEquals(0, row.total().compareTo(c.jsonPath().getObject("total", BigDecimal.class)));
    }

}
