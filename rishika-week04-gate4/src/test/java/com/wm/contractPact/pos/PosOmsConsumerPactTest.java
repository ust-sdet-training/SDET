package com.wm.contractPact.pos;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "oms-provider",
        pactVersion = PactSpecVersion.V4)
public class PosOmsConsumerPactTest {

    @Pact(provider = "oms-provider", consumer = "pos-consumer")
    V4Pact getOrder501(PactDslWithProvider builder) {

        return builder
                .given("Order 501 exists")
                .uponReceiving("Fetch order 501")
                .path("/order/501")
                .method("GET")

                .willRespondWith()
                .status(200)
                .matchHeader("Content-Type",
                        "application/json(;.*)?",
                        "application/json")

                .body(new PactDslJsonBody()
                        .integerType("orderId",501)
                        .stringType("customerName","Rishika")
                        .stringType("status","SHIPPED")
                        .numberType("amount",1599.50))

                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "getOrder501")
    void verifyOrder501(MockServer mockServer){

        OmsClient client = new OmsClient(mockServer.getUrl());

        OmsClient.Order order = client.getOrder(501);

        assertEquals(501,order.orderId());
        assertEquals("Rishika",order.customerName());
        assertEquals("SHIPPED",order.status());
        assertEquals(1599.50,order.amount());

    }

    @Pact(provider = "oms-provider", consumer = "pos-consumer")
    V4Pact getOrder700(PactDslWithProvider builder){

        return builder

                .given("Order 700 exists")
                .uponReceiving("Fetch order 700")
                .path("/order/700")
                .method("GET")
                .willRespondWith()
                .status(200)
                .matchHeader("Content-Type",
                        "application/json(;.*)?",
                        "application/json")
                .body(new PactDslJsonBody()
                        .integerType("orderId",700)
                        .stringType("customerName","John")
                        .stringType("status","PROCESSING")
                        .numberType("amount",850))
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "getOrder700")
    void verifyOrder700(MockServer mockServer){

        OmsClient client = new OmsClient(mockServer.getUrl());

        OmsClient.Order order = client.getOrder(700);
        assertEquals(700,order.orderId());
        assertEquals("John",order.customerName());
        assertEquals("PROCESSING",order.status());
        assertEquals(850,order.amount());
    }

    @Pact(provider = "oms-provider", consumer = "pos-consumer")
    V4Pact createOrder(PactDslWithProvider builder){

        return builder

                .given("Provider accepts new orders")
                .uponReceiving("Create a new order")
                .path("/order")
                .method("POST")
                .matchHeader("Content-Type",
                        "application/json(;.*)?",
                        "application/json")
                .body(new PactDslJsonBody()
                        .stringType("customerName","Rishika")
                        .stringType("product","Laptop")
                        .integerType("quantity",1))
                .willRespondWith()
                .status(201)
                .matchHeader("Content-Type",
                        "application/json(;.*)?",
                        "application/json")
                .body(new PactDslJsonBody()
                        .integerType("orderId",501)
                        .stringType("status","CREATED")
                        .numberType("amount",1599.50))
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "createOrder")
    void verifyCreateOrder(MockServer mockServer){

        OmsClient client = new OmsClient(mockServer.getUrl());

        OmsClient.CreateOrderRequest request =
                new OmsClient.CreateOrderRequest(
                        "Rishika",
                        "Laptop",
                        1);

        OmsClient.CreateOrderResponse response =
                client.createOrder(request);

        assertEquals(501,response.orderId());
        assertEquals("CREATED",response.status());
        assertEquals(1599.50,response.amount());

    }

}