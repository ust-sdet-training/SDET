package com.ust.sdet.api.tests;

import com.ust.sdet.api.config.DatabaseConfig;
import com.ust.sdet.api.model.OrderRow;
import com.ust.sdet.api.support.DbSupport;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ust.sdet.api.specification.CommonSpec.commonReqSpecXML;
import static com.ust.sdet.api.specification.CommonSpec.commonResSpec;
import static com.ust.sdet.api.specification.LoginSpec.apiKeyReqSpec;
import static com.ust.sdet.api.specification.OrderSpec.orderReqSpec;
import static com.ust.sdet.api.specification.OrderSpec.orderResSpec;
import static com.ust.sdet.api.support.Utils.*;
import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTests {
    private static final List<Long> idGenerated = new ArrayList<>();
    private static DbSupport DATABASE;
    private static String TOKEN;

    @BeforeAll
    static void setup() {
        DATABASE = new DbSupport(DatabaseConfig.fromEnvironment());
        TOKEN = token();
    }

    @AfterAll
    static void clean_DB() throws SQLException {
        for(long i : idGenerated)
            DATABASE.deleteOrder(i);
        idGenerated.clear();
    }

    @Test
    @DisplayName("Local M1: MySQL is reachable through JDBC")
    void localMySqlIsReachable() throws Exception {
        assertTrue(DATABASE.isReachable());
    }

    @Test
    @DisplayName("M2: Login Success Test")
    void loginSuccessFlow(){
        token();
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
                .body("items.size()",notNullValue())
                .body("items.size()",equalTo(2))
                .body("status", equalTo("CREATED"))
                .extract().response();

        long id = c.jsonPath().getLong("orderId");
        idGenerated.add(id);
        OrderRow row = DATABASE.findOrder(id);
        assertNotNull(row, "order must be persisted");
        assertEquals("CREATED", row.status());
        assertEquals(0, row.total().compareTo(c.jsonPath().getObject("total", BigDecimal.class)));

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

    @Test
    @DisplayName("M9: Get /Orders All API KEY")
    void getOrderedProducts(){
        given()
                .spec(apiKeyReqSpec("/partner/orders"))
                .when()
                .get("/{id}", 5001)
                .then()
                .spec(commonResSpec())
                .body(matchesJsonSchemaInClasspath("schemas/json/partner-order.schema.json"))
                .body("partner",notNullValue())
                .body("order.items.size()", greaterThan(0) );
    }

}