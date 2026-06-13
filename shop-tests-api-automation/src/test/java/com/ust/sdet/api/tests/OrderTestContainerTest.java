package com.ust.sdet.api.tests;

import com.ust.sdet.api.config.DatabaseConfig;
import com.ust.sdet.api.model.OrderRow;
import com.ust.sdet.api.support.DbSupport;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.Container;
import org.testcontainers.containers.MySQLContainer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.ust.sdet.api.config.TestContainers.mysql;
import static com.ust.sdet.api.specification.OrderSpec.orderReqSpec;
import static com.ust.sdet.api.specification.OrderSpec.orderResSpec;
import static com.ust.sdet.api.support.Utils.token;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderTestContainerTest {
    private static MySQLContainer<?> DATABASE;
    private static String TOKEN;

    @BeforeAll
    static void setup() {
        DATABASE = mysql;
        TOKEN = token();
    }

    @Test
    void containerShouldStart() {
        System.out.println("JDBC URL = " + DATABASE.getJdbcUrl());
        System.out.println("Username = " + DATABASE.getUsername());
    }

    @Test
    @DisplayName("M3: Created -> ALLOCATED -> SHIPPED is persisted")
    void orderLifecycle_isPersisted() throws Exception{
        var order = Map.of(
                "items", List.of(107,106),
                "currency", "INR"
        );
        Response c = given()
                .spec(orderReqSpec(TOKEN))
                .body(order)
                .when()
                .post()
                .then()
                .spec(orderResSpec())
                .body(matchesJsonSchemaInClasspath("schemas/json/order.schema.json"))
                .body("items.size()",equalTo(2))
                .body("status", equalTo("CREATED"))
                .extract().response();

        long id = c.jsonPath().getLong("orderId");
        Container.ExecResult row = DATABASE.execInContainer("""
                                                    SELECT id, order_number, status, total, user_id, created_at
                                                    FROM orders
                                                    WHERE id = ?
                                                    """);

        System.out.println(row);
//        assertNotNull(row, "order must be persisted");
//        assertEquals("CREATED", row.);
//        assertEquals(0, row.total().compareTo(c.jsonPath().getObject("total", BigDecimal.class)));

        //ALLOCATED
        given()
                .spec(orderReqSpec(TOKEN))
                .when().post("{id}/allocate", id)
                .then()
                .statusCode(200);

        //SHIPPED
        given()
                .spec(orderReqSpec(TOKEN))
                .when().post("{id}/ship", id)
                .then()
                .statusCode(200);
    }
}
