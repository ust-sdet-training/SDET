package com.ust.sdet.api.apiframework.test;

import com.ust.sdet.api.apiframework.auth.TokenManager;
import com.ust.sdet.api.apiframework.base.BaseTest;
import com.ust.sdet.api.apiframework.config.ConfigManager;
import com.ust.sdet.api.apiframework.spec.AllSpec;
import com.ust.sdet.api.apiframework.testData.ModelObject;

import com.ust.sdet.api.dbframework.assertions.DbAssertions;
import com.ust.sdet.api.dbframework.config.DatabaseConfig;
import com.ust.sdet.api.dbframework.model.OrderRow;
import com.ust.sdet.api.dbframework.support.DBSupport;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class MainTest extends BaseTest {

    DatabaseConfig config = DatabaseConfig.fromEnvironment();
    DBSupport dbSupport = new DBSupport(config);

    private final List<Long> createdOrderIds = new ArrayList<>();

    @AfterEach
    void cleanCreatedRows() {

        if (createdOrderIds.isEmpty()) {
            return;
        }
        for (Long orderId : createdOrderIds) {
            try {
                int deletedRows = dbSupport.deleteOrder(orderId);

                System.out.println("Deleted Order ID: " + orderId + " | Rows Deleted: " + deletedRows);

            } catch (Exception e) {
                System.out.println("Failed to delete Order ID: " + orderId);
            }
        }

        createdOrderIds.clear();
    }


    @Test
    @DisplayName("Create Order")
    void createOrder() {

        String token = TokenManager.getOpsToken();

        given()
                .baseUri(ConfigManager.BASE_URL)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(ModelObject.createOrder())

                .when()
                .post("/api/secure/orders")

                .then()
                .statusCode(201)
                .body("status", equalTo("CREATED"))
                .body(matchesJsonSchemaInClasspath("schemas/json/ordersecure.schema.json"));
    }

    @Test
    @DisplayName("Get Existing Order")
    void getOrder() {

        String token = TokenManager.getOpsToken();

        Integer orderId = TokenManager.createSecureOrder(token);
        createdOrderIds.add(orderId.longValue());

        given()
                .spec(AllSpec.secureOrdersSpec())
                .pathParam("id", orderId)

                .when()
                .get("/{id}")

                .then()
                .statusCode(200)
                .body("id", equalTo(orderId));
    }


    @Test
    @DisplayName("Missing Token")
    void missingTokenReturns401() {

        given()
                .baseUri(ConfigManager.BASE_URL)

                .when()
                .get("/api/secure/orders/5001")

                .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Expired Token")
    void expiredTokenReturns401() {

        given()
                .baseUri(ConfigManager.BASE_URL)
                .auth().oauth2(TokenManager.getExpiredToken())

                .when()
                .get("/api/secure/orders/5001")

                .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Viewer Cannot Create Order")
    void viewerCannotCreateOrder() {

        given()
                .baseUri(ConfigManager.BASE_URL)
                .contentType(ContentType.JSON)
                .auth().oauth2(TokenManager.getViewerToken())
                .body(ModelObject.createOrder())

                .when()
                .post("/api/secure/orders")

                .then()
                .statusCode(403);
    }

    @Test
    @DisplayName("Customer Flow With DB Validation")
    void debugOrderFlow() {

        String userToken = TokenManager.loginAndGetToken();

        TokenManager.clearCart(userToken);
        TokenManager.addItem(userToken);

        Integer orderId = TokenManager.createOrder(userToken);

        createdOrderIds.add(orderId.longValue());

        System.out.println("Customer Order Id = " + orderId);
    }

    @Test
    @DisplayName("Order Lifecycle Flow With DB Validation")
    void createOrder_thenReadItBack() throws SQLException {

        String opsToken = TokenManager.getOpsToken();

        Integer orderId = TokenManager.createSecureOrder(opsToken);
        createdOrderIds.add(orderId.longValue());

        System.out.println("Order Id = " + orderId);

        OrderRow dbOrder = dbSupport.findOrder(orderId);
        DbAssertions.assertOrderExists(dbOrder);
        DbAssertions.assertOrderStatus(dbOrder, "CREATED");

        TokenManager.allocateOrder(opsToken, orderId);
        dbOrder = dbSupport.findOrder(orderId);
        DbAssertions.assertOrderStatus(dbOrder, "ALLOCATED");

        TokenManager.shipOrder(opsToken, orderId);
        dbOrder = dbSupport.findOrder(orderId);
        DbAssertions.assertOrderStatus(dbOrder, "SHIPPED");

        TokenManager.getSecureOrder(opsToken, orderId);
    }

    @Test
    @DisplayName("Negative Path - Ship Before Allocate")
    void shipBeforeAllocate() {

        String opsToken = TokenManager.getOpsToken();

        Integer orderId = TokenManager.createSecureOrder(opsToken);
        createdOrderIds.add(orderId.longValue());

        given()
                .spec(AllSpec.secureOrders(opsToken))
                .pathParam("id", orderId)

                .when()
                .post("/{id}/ship")

                .then()
                .statusCode(409)
                .body("message", equalTo("Cannot move order from CREATED to SHIPPED"))
                .body("currentStatus", equalTo("CREATED"))
                .body("expectedStatus", equalTo("ALLOCATED"));
    }
}
