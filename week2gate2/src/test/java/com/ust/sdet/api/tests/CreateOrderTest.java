package com.ust.sdet.api.tests;

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
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;

import java.util.ArrayList;
import java.util.List;

public class CreateOrderTest {

    private final List<Long> createdOrderIds = new ArrayList<>();

    private static final String BASE_URL = ApiConfig.BASE_URL;

    DatabaseConfig config = DatabaseConfig.fromEnvironment();
    DbSupport dbSupport = new DbSupport(config);
    OrderRepository repository = new OrderRepository(dbSupport);

    @BeforeAll
    static void setup() {

        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";


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
                        .body(matchesJsonSchemaInClasspath("schemas/json/orders_schema/partner-orders.schema.json"))
                        .extract()
                        .response();

        assertEquals("CREATED", response.jsonPath().getString("status"));

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
    }

}