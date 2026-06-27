package com.ust.consumer;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.RequestResponsePact;

public class PactProvider {

    public static RequestResponsePact createProductPact(PactDslWithProvider builder) {

        PactDslJsonBody responseBody = new PactDslJsonBody()
                .integerType("priceMinor", 129900)
                .stringValue("availability", "IN_STOCK");

        return builder
                .given("SKU-1 exists")
                .uponReceiving("Request for existing product")
                .path("/catalog/SKU-1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(responseBody)
                .toPact();
    }
}