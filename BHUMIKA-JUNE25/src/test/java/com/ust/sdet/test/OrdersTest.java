package com.ust.sdet.test;

import com.ust.sdet.baseTest.BaseTest;
import com.ust.sdet.dataModel.OrderObj;
import com.ust.sdet.spec.AllSpec;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrdersTest  extends BaseTest {
        static int orderId = 10;

        @Order(1)
        @Test
        void createOrder() {

            OrderObj order = new OrderObj(
                    orderId,
                    1,
                    1,
                    "2026-06-25T06:22:53.636Z",
                    "placed",
                    true
            );

            given(requestSpec)
                    .body(order)

                    .when()
                    .post("/store/order")

                    .then()
                    .spec(AllSpec.successResponse());
        }

        @Order(2)
        @Test
        void getOrder() {

            given(requestSpec)
                    .pathParam("orderId", orderId)

                    .when()
                    .get("/store/order/{orderId}")

                    .then()
                    .spec(AllSpec.successResponse())
                    .body(matchesJsonSchemaInClasspath("schemas.json/order.schema.json"));

        }

        @Order(3)
        @Test
        void deleteOrder() {

            given(requestSpec)
                    .pathParam("orderId", orderId)

                    .when()
                    .delete("/store/order/{orderId}")

                    .then()
                    .statusCode(200);
        }

        @Test
        void invalidOrder() {

            given(requestSpec)
                    .pathParam("orderId", 9)

                    .when()
                    .get("/store/order/{orderId}")

                    .then()
                    .statusCode(404);
        }
    }

