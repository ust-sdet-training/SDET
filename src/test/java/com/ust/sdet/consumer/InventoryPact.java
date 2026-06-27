package com.ust.sdet.consumer;


import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;

public class InventoryPact {

    @Pact(provider = "oms-provider", consumer = "pos-consumer")
    public V4Pact getInventory(PactDslWithProvider builder) {

        return builder
                .given("SKU-9 has Stock")
                .uponReceiving("a request for inventory")
                .path("/Inventory/SKU-9")
                .method("GET")

                .willRespondWith()
                .status(200)
                .matchHeader("Content-Type", "application/json(;.*)?", "application/json")

                .body(new PactDslJsonBody()
                        .integerType("statusCode", 200)
                        .stringType("sku", "SKU-9")
                        .integerType("quantity", 20)
                )

                .toPact(V4Pact.class);
    }
}
