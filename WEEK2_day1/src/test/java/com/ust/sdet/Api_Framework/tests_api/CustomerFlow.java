package com.ust.sdet.Api_Framework.tests_api;

import com.ust.sdet.Api_Framework.config.ApiConfig;
import com.ust.sdet.Api_Framework.model.OrderModel;
import com.ust.sdet.Api_Framework.specs.SpecFactory;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerFlow {

    private static String customerToken;

    private static final String BASE_URL = ApiConfig.BASE_URL;

    @BeforeAll
    static void setup() {

        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";

        customerToken = SpecFactory.getCustomerToken();
    }

    @Test
    @DisplayName("Customer can login and add Running Shoes to cart then checkout and view orders")
    void customer_can_order_product() throws SQLException {


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
                .spec(SpecFactory.customerRead(customerToken))

                .when()
                .get(SpecFactory.productsPath())

                .then()
                .spec(SpecFactory.ok200())
                .body(matchesJsonSchemaInClasspath("schema/json/product-list.schema.json"))
                .body("items.size()", greaterThan(0))
                .body("items[0].name", equalTo("Running Shoes"))
                .body("items[0].stock", equalTo(18))
                .body("items[0].category", equalTo("Footwear"));


        given()
                .spec(SpecFactory.customerRead(customerToken))

                .when()
                .delete(SpecFactory.cartPath())

                .then()
                .spec(SpecFactory.noContent204());


        given()
                .spec(SpecFactory.customerWrite(customerToken))
                .body(item)

                .when()
                .post(SpecFactory.cartItemsPath())

                .then()
                .statusCode(anyOf(equalTo(200), equalTo(201)));


        OrderModel ord = given()
                .spec(SpecFactory.customerWrite(customerToken))
                .body(order)

                .when()
                .post(SpecFactory.ordersPath())

                .then()
                .log().all()
                .spec(SpecFactory.created201())
                .body(matchesJsonSchemaInClasspath("schema/json/orders_schema/customer-orders.schema.json"))
                .extract()
                .as(OrderModel.class);

        assertEquals("Confirmed", ord.status());
        assertEquals("Paid", ord.payment());

    }
}