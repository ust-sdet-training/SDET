package com.ust.sdet.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "catalog-service",
        pactVersion = PactSpecVersion.V4)

public class CatalogConsumerPactTest {

    @Pact(provider="catalog-service", consumer = "storefront")
    V4Pact skuExists(PactDslWithProvider builder){
        return builder
                .given("SKU-1 exists")
                .uponReceiving("Get existing SKU")
                .path("/catalog/SKU-1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .matchHeader("Content-Type","application/json(;.*)?", "application/json")
                .body(new PactDslJsonBody()
                        .stringType("sku", "SKU-1")
                        .stringType("name", "Macbook Air")
                        .integerType("price", 90000)
                        .stringMatcher("availability", "In_Stock|Low_Stock|Out_Of_Stock", "In_Stock")   )
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "skuExists")
    void testSkuExists(MockServer mockServer) {
        CatalogClient client = new CatalogClient(mockServer.getUrl());
        CatalogClient.Product product = client.getSku("SKU-1");
        assertEquals("SKU-1", product.sku());
        assertEquals("Macbook Air", product.name());
        assertEquals(90000, product.price());
        assertEquals("In_Stock", product.availability());
    }

    @Pact(provider = "catalog-service", consumer = "storefront")
    V4Pact skuNotFound(PactDslWithProvider builder) {
        return builder
                .given("SKU-404 does not exist")
                .uponReceiving("GET missing SKU")
                .path("/catalog/SKU-404")
                .method("GET")
                .willRespondWith()
                .status(404)
                .matchHeader("Content-Type", "application/json(;.*)?", "application/json")
                .body(new PactDslJsonBody()
                        .stringType("message", "SKU not found"))
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "skuNotFound")
    void testSkuNotFound(MockServer mockServer) {
        CatalogClient client = new CatalogClient(mockServer.getUrl());
        String message = client.getMissingSku("SKU-404");
        assertEquals("SKU not found", message);
    }



}
