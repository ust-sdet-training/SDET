package com.ust.sdet.consumer;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;

public class InvalidOrderPact {

//    @Pact(provider = "oms-provider", consumer = "pos-consumer")
//    public V4Pact pact(PactDslWithProvider builder) {
//
//        return builder
//                .given("Order 1234 not exists")
//                .uponReceiving("GET order")
//                .path("/orders/1234")
//                .method("POST")
//                .willRespondWith()
//                .status(404)
//                .body("""
//                {"message":"Invalid Product"}
//                """)
//                .toPact(V4Pact.class);
//    }
}

