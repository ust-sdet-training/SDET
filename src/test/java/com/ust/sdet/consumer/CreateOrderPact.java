package com.ust.sdet.consumer;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;

public class CreateOrderPact {

    @Pact(provider = "oms-provider", consumer = "pos-consumer")
    public V4Pact createOrder(PactDslWithProvider builder) {

        return builder
                .given("Creating a new order")
                .uponReceiving("a request to create order")
                .path("/orders/")
                .method("POST")
                .matchHeader("Content-Type", "application/json(;.*)?", "application/json")

                .body(new PactDslJsonBody()
                        .stringType("sku", "SKU-9")
                        .integerType("quantity", 20)
                )

                .willRespondWith()
                .status(201)
                .matchHeader("Content-Type", "application/json(;.*)?", "application/json")

                .body(new PactDslJsonBody()
                        .integerType("statusCode", 201)
                        .integerType("orderId", 101)
                        .stringType("status", "CREATED")
                        .numberType("total", 2000.0)
                )

                .toPact(V4Pact.class);
    }
}
