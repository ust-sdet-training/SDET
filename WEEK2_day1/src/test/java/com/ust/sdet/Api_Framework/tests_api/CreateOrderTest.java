package com.ust.sdet.Api_Framework.tests_api;

import com.ust.sdet.Api_Framework.specs.SpecFactory;
import com.ust.sdet.Api_Framework.config.ApiConfig;
import com.ust.sdet.dbframework.dbTests.Db_Assertions;
import com.ust.sdet.dbframework.config.DatabaseConfig;
import com.ust.sdet.dbframework.model.OrderRow;
import com.ust.sdet.dbframework.repository.OrderRepo;
import com.ust.sdet.dbframework.support.DbSupport;
import com.ust.sdet.dbframework.support.TestEnvironment;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;

import java.util.ArrayList;
import java.util.List;

public class CreateOrderTest {

    private final List<Long> createdOrderIds = new ArrayList<>();
    private static String token;

    private static final String BASE_URL = ApiConfig.BASE_URL;
    DatabaseConfig config =
            DatabaseConfig.fromEnvironment();

    DbSupport dbSupport =
            new DbSupport(config);
    OrderRepo repository =
            new OrderRepo(dbSupport);

    @BeforeAll
    static void setup() {

        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";

        token = SpecFactory.getCustomerToken();

        System.out.println("Base URI : " + RestAssured.baseURI);
        System.out.println("Base Path: " + RestAssured.basePath);
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
    void shouldCreateOrderAndValidateDatabase() throws SQLException {

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
                        .body(matchesJsonSchemaInClasspath("schema/json/orders_schema/partner-orders.schema.json"))
                        .extract()
                        .response();

        assertEquals("CREATED", response.jsonPath().getString("status"));

        long orderId = response.jsonPath().getLong("id");

        createdOrderIds.add(orderId);

        OrderRow Order = repository.findOrder(orderId);

        Db_Assertions.OrderExistsorNot(Order);

        Db_Assertions.OrderStatus(
                Order,
                response.jsonPath().getString("status")
        );

        Db_Assertions.OrderNumber(
                Order,
                response.jsonPath().getString("orderNumber")
        );
    }
}