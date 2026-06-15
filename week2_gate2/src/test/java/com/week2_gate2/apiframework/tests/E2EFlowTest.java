package com.week2_gate2.apiframework.tests;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.week2_gate2.apiframework.support.APISupport;
import com.week2_gate2.dbframework.config.DatabaseConfig;
import com.week2_gate2.dbframework.model.product;
import com.week2_gate2.resources.fixture.TestEnvironmentVariables;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import com.week2_gate2.dbframework.support.DbSupport;

public class E2EFlowTest {
    private static String token;
    private static String invalid_token;
    private static String wrong_role_token;
    private static RequestSpecification authedOrders;
    private static RequestSpecification wrongRoleOrder;
    private static RequestSpecification invalidTokenOrder;
    private static RequestSpecification missingTokenOrder;
    private static DbSupport database;

    @BeforeAll
    static void setup() {
        token = APISupport.fetchToken(TestEnvironmentVariables.TOKEN_URL_CLIENT_ID(), TestEnvironmentVariables.TOKEN_URL_CLIENT_SECRET());
        invalid_token = APISupport.fetchToken(TestEnvironmentVariables.TOKEN_URL_CLIENT_ID(), TestEnvironmentVariables.TOKEN_URL_CLIENT_SECRET()) + "1";
        wrong_role_token = APISupport.fetchToken(TestEnvironmentVariables.VIEWER_CLIENT_ID(), TestEnvironmentVariables.VIEWER_CLIENT_SECRET());
        authedOrders = APISupport.authedOrders(token);
        invalidTokenOrder = APISupport.authedOrders(invalid_token);
        wrongRoleOrder = APISupport.authedOrders(wrong_role_token);
        missingTokenOrder = APISupport.missing_authed();
        database = new DbSupport(DatabaseConfig.fromEnvironment());
    }

    @Test
    @DisplayName("Create, allocate and ship an order and assert each are working and then delete order")
    void createAllocateAndShipOrder() throws SQLException {
        Response created_response = given()
                                .spec(authedOrders)
                                .body(Map.of(
                                    "items", List.of(101, 107),
                                    "currency", "INR"
                                ))
                            .when()
                                .post("")
                            .then()
                                .log().all()
                                .statusCode(201)
                                .extract()
                                .response();
        Integer id = created_response.path("orderId");
        product row = database.findOrder(id);
        assertNotNull(row, "order must be persisted");
        assertEquals("CREATED", row.status());
        assertEquals(0, row.total().compareTo(created_response.jsonPath().getObject("total", BigDecimal.class)));

        Response allocated_response = given()
                                .spec(authedOrders)
                            .when()
                                .post("/{id}/allocate", id)
                            .then()
                                .log().all()
                                .statusCode(200)
                                .extract()
                                .response();
        id = allocated_response.path("orderId");
        row = database.findOrder(id);
        assertNotNull(row, "order must be persisted");
        assertEquals("ALLOCATED", row.status());

        Response shipped_response = given()
                                .spec(authedOrders)
                            .when()
                                .post("/{id}/ship", id)
                            .then()
                                .log().all()
                                .statusCode(200)
                                .extract()
                                .response();
        id = shipped_response.path("orderId");
        row = database.findOrder(id);
        assertNotNull(row, "order must be persisted");
        assertEquals("SHIPPED", row.status());

        id = database.deleteOrder(id);
        assertEquals(id, 1);
    }

    @Test
    @DisplayName("401 Invalid token error")
    void invalidTokenError() {
        String message = given()
            .spec(invalidTokenOrder)
        .when()
            .get("/{id}", 5001)
        .then()
            .log().all()
            .statusCode(401)
            .extract()
            .path("message");
        assertEquals(message, "Invalid access token");
    }

    @Test
    @DisplayName("401 Missing token error")
    void missingTokenError() {
        String message = given()
            .spec(missingTokenOrder)
        .when()
            .get("/{id}", 5001)
        .then()
            .log().all()
            .statusCode(401)
            .extract()
            .path("message");
        assertEquals(message, "Authentication required");
    }

    @Test
    @DisplayName("403 invalid role error")
    void invalidRoleError() {
        String message = given()
            .spec(wrongRoleOrder)
            .body(Map.of(
                "items", List.of(101, 107),
                 "currency", "INR"
             ))
        .when()
            .post("")
        .then()
            .statusCode(403)
            .extract()
            .path("message");
        assertEquals(message, "OPS role required");
    }

    @Test
    @DisplayName("404 data not found")
    void securedGet() {
        String message = given()
            .spec(authedOrders)
        .when()
            .get("/{id}", 0)
        .then()
        .log().all()
            .statusCode(404)
            .extract()
            .path("message");
        assertEquals(message, "Order not found");
    }

}
