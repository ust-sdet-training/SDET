package com.week4.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "catalog-service")
public class ConsumerPactTest {

    @Pact(consumer = "storefront-web", provider = "catalog-service")
    public V4Pact pact(PactBuilder builder) {

        return builder
                .usingLegacyDsl()
                .given("SKU-1 exists")
                .uponReceiving("GET catalog SKU-1")
                .path("/catalog/SKU-1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body("""
                    {
                      "sku":"SKU-1",
                      "availability":"IN_STOCK",
                      "priceMinor":129900
                    }
                    """)
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "pact")
    void testCatalog(MockServer mockServer) throws Exception {

        URL url = new URL(mockServer.getUrl() + "/catalog/SKU-1");
        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        int statusCode = connection.getResponseCode();

        assertEquals(200, statusCode);
    }

    @Pact(consumer = "storefront-web", provider = "catalog-service")
    public V4Pact skuMissing(PactDslWithProvider builder) {

        return builder
                .given("SKU missing")
                .uponReceiving("GET missing SKU")
                .path("/catalog/SKU-999")
                .method("GET")
                .willRespondWith()
                .status(404)
                .body("""
                {
                  "message":"Product not found"
                }
                """)
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "skuMissing")
    void testMissingSku(MockServer mockServer) throws Exception {
        URL url = new URL(mockServer.getUrl() + "/catalog/SKU-999");
        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        int statusCode = connection.getResponseCode();

        assertEquals(404, statusCode);
    }
}