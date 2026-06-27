package com.ust.consumer;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.RequestResponsePact;

public class PactProvider {

    public static RequestResponsePact skuExists(PactDslWithProvider builder) {

        return builder
                .given("SKU-1 exists")
                .uponReceiving("Get Product")
                .path("/catalog/SKU-1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody()
                        .integerType("priceMinor",129900)
                        .stringValue("availability", "IN_STOCK"))
                .toPact();
    }
}