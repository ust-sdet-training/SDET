package com.ust.sdet.consumer;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;

public class GetOrderPact {

    @Pact(provider = "oms-provider", consumer = "pos-consumer")
    public V4Pact pact(PactDslWithProvider builder) {

        return builder
                .given("Order 12 exists")
                .uponReceiving("GET order")
                .path("/orders/12")
                .method("GET")
                .willRespondWith()
                .status(200)
                .matchHeader("Content-Type", "application/json(;.*)?", "application/json")
                .body(new PactDslJsonBody()
                        .integerType("orderId", 12)
                        .stringType("status", "CONFIRMED")
                        .numberType("total", 42.00))
                .toPact(V4Pact.class);
    }
}

