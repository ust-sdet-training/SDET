package com.ust.sdet.api.contract.pos;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "oms-provider", pactVersion = PactSpecVersion.V4)
class PosOmsConsumerPactTest {

        // GET ORDER SKU-1 786


        @Pact(provider = "oms-provider", consumer = "pos-consumer")
        V4Pact getOrder(PactDslWithProvider builder) {

                return builder
                                .given("Sku-786 is exist")

                                .uponReceiving("a request for Sku-786")
                                .path("/order/7")
                                .method("GET")

                                .willRespondWith()
                                .status(200)

                                .matchHeader(
                                                "Content-Type",
                                                "application/json(;.*)?",
                                                "application/json")

                                .body(new PactDslJsonBody()
                                                .integerType("id", 7)
                                                .stringType("status", "Confirmed")
                                                .numberType("total", 42))

                                .toPact(V4Pact.class);
        }

        @Test
        @PactTestFor(pactMethod = "getOrder")
        void testGetInventory(MockServer mockServer) {

                OmsClient omsClient = new OmsClient(mockServer.getUrl());
                OmsClient.Order order = omsClient.getOrder(7);

                assertEquals(7, order.id());
                assertEquals("Confirmed", order.status());
                assertEquals(42.0, order.total());
        }

       

        // order not exists
        @Pact(provider = "oms-provider", consumer = "pos-consumer")
        V4Pact getOrderNotFound(PactDslWithProvider builder) {

                return builder
                                .given("Order Sku-786 does not exist")

                                .uponReceiving("request for missing order")
                                .path("/order/786")
                                .method("GET")

                                .willRespondWith()
                                .status(404)

                                .matchHeader(
                                                "Content-Type",
                                                "application/json(;.*)?",
                                                "application/json")

                                .body(new PactDslJsonBody()
                                                .stringType("message", "Order not found"))

                                .toPact(V4Pact.class);
        }

        @Test
        @PactTestFor(pactMethod = "getOrderNotFound")
        void testOrderNotFound(MockServer mockServer) {

                Response response = given()
                                .baseUri(mockServer.getUrl())
                                .when()
                                .get("/order/786")
                                 .then()
                                .statusCode(404)
                                .extract()
                                .response();

                response.then().statusCode(404);

                assertEquals("Order not found",
                                response.jsonPath().getString("message"));
        }

         // CREATE ORDER

        @Pact(provider = "oms-provider", consumer = "pos-consumer")
        V4Pact createOrder(PactDslWithProvider builder) {

                return builder

                                .given("Provider can create orders")

                                .uponReceiving("a request to create an order")
                                .path("/order")
                                .method("POST")

                                .matchHeader(
                                                "Content-Type",
                                                "application/json(;.*)?",
                                                "application/json")

                                .body(new PactDslJsonBody()
                                                .integerType("statusCode", 0)
                                                .integerType("orderId", 786)
                                                .stringType("status", "NEW")
                                                .numberType("total", 42.0))

                                .willRespondWith()

                                .status(201)

                                .matchHeader(
                                                "Content-Type",
                                                "application/json(;.*)?",
                                                "application/json")

                                .body(new PactDslJsonBody()
                                                .integerType("statusCode", 201)
                                                .integerType("orderId", 786)
                                                .stringType("status", "CREATED")
                                                .numberType("total", 42.0))

                                .toPact(V4Pact.class);
        }

        @Test
        @PactTestFor(pactMethod = "createOrder")
        void testCreateOrder(MockServer mockServer) {

                OmsClient omsClient = new OmsClient(mockServer.getUrl());

                OmsClient.CreateOrderRequest request = new OmsClient.CreateOrderRequest(
                                0,
                                786,
                                "NEW",
                                42.0);

                OmsClient.CreateOrderResponse response = omsClient.createOrder(request);

                assertEquals(786, response.orderId());
                assertEquals("CREATED", response.status());
                assertEquals(42.0, response.total());
        }

      
        // update order
        @Pact(provider = "oms-provider", consumer = "pos-consumer")
        V4Pact updateOrder(PactDslWithProvider builder) {

                return builder

                                .given("Order 786 can be updated")

                                .uponReceiving("update order 786")

                                .path("/order/786")
                                .method("PUT")

                                .matchHeader(
                                                "Content-Type",
                                                "application/json(;.*)?",
                                                "application/json")

                                .body(new PactDslJsonBody()
                                                .stringType("status", "SHIPPED")
                                                .numberType("total", 42.0))

                                .willRespondWith()

                                .status(200)

                                .matchHeader(
                                                "Content-Type",
                                                "application/json(;.*)?",
                                                "application/json")

                                .body(new PactDslJsonBody()
                                                .integerType("id", 786)
                                                .stringType("status", "SHIPPED")
                                                .numberType("total", 42.0))

                                .toPact(V4Pact.class);
        }

        @Test
        @PactTestFor(pactMethod = "updateOrder")
        void testUpdateOrder(MockServer mockServer) {

                Response response = given()
                                .baseUri(mockServer.getUrl())
                                .contentType(ContentType.JSON)
                                .body("""
                                                {
                                                  "status":"SHIPPED",
                                                  "total":42.0
                                                }
                                                """)
                                .put("/order/786")
                                .then()
                                .statusCode(200)
                                .extract()
                                .response();

                response.then().statusCode(200);

                assertEquals("SHIPPED",
                                response.jsonPath().getString("status"));
        }

        // patch order

        @Pact(provider = "oms-provider", consumer = "pos-consumer")
        V4Pact patchOrder(PactDslWithProvider builder) {

                return builder

                                .given("Order 786 status can be patched")

                                .uponReceiving("patch order status")

                                .path("/order/786")
                                .method("PATCH")

                                .matchHeader(
                                                "Content-Type",
                                                "application/json(;.*)?",
                                                "application/json")

                                .body(new PactDslJsonBody()
                                                .stringType("status", "DELIVERED"))

                                .willRespondWith()

                                .status(200)

                                .matchHeader(
                                                "Content-Type",
                                                "application/json(;.*)?",
                                                "application/json")

                                .body(new PactDslJsonBody()
                                                .integerType("id", 786)
                                                .stringType("status", "DELIVERED")
                                                .numberType("total", 42.0))

                                .toPact(V4Pact.class);
        }

        @Test
        @PactTestFor(pactMethod = "patchOrder")
        void testPatchOrder(MockServer mockServer) {

                Response response = given()
                                .baseUri(mockServer.getUrl())
                                .contentType(ContentType.JSON)
                                .body("""
                                                {
                                                  "status":"DELIVERED"
                                                }
                                                """)
                                .patch("/order/786")
                                .then()
                                .statusCode(200)
                                .extract()
                                .response();

                response.then().statusCode(200);

                assertEquals("DELIVERED",
                                response.jsonPath().getString("status"));
        }

        // delelte

        @Pact(provider = "oms-provider", consumer = "pos-consumer")
        V4Pact deleteOrder(PactDslWithProvider builder) {

                return builder

                                .given("Order 786 can be deleted")
                                .uponReceiving("delete order 786")
                                .path("/order/786")
                                .method("DELETE")
                                .willRespondWith()
                                .status(204)
                                .toPact(V4Pact.class);
        }

        @Test
        @PactTestFor(pactMethod = "deleteOrder")
        void testDeleteOrder(MockServer mockServer) {

             Response response =                    given()
                                .baseUri(mockServer.getUrl())
                                .when()
                                .delete("/order/786")
                                .then()
                                .statusCode(204)
                                .extract()
                                .response();

                        assertEquals(204, response.getStatusCode());
        }

}