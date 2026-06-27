package com.assessment.pos.contract;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.assessment.pos.client.CatalogApiClient;
import com.assessment.pos.model.ProductResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "oms", port = "0")
public class CatalogConsumerPactTest {

    @Pact(consumer = "pos")
    public V4Pact createPact(PactBuilder builder) {
        PactDslJsonBody successBody = new PactDslJsonBody()
                .stringType("sku", "RUN-101")
                .stringType("name", "Running Shoes")
                .numberType("price", 120.50)
                .booleanType("availability", true);

        PactDslJsonBody missingBody = new PactDslJsonBody()
                .stringType("message", "Product not found");

        builder.given("SKU exists")
                .expectsToReceiveHttpInteraction("a request for RUN-101", interaction -> interaction
                        .withRequest(request -> request.method("GET").path("/catalog/RUN-101"))
                        .willRespondWith(response -> response.status(200).body(successBody)));

        builder.given("SKU missing")
                .expectsToReceiveHttpInteraction("a request for UNKNOWN", interaction -> interaction
                        .withRequest(request -> request.method("GET").path("/catalog/UNKNOWN"))
                        .willRespondWith(response -> response.status(404).body(missingBody)));

        return builder.toPact();
    }

    @Test
    void shouldHandleCatalogResponses(MockServer mockServer) {
        CatalogApiClient client = new CatalogApiClient(mockServer.getUrl());

        ProductResponse product = client.getProduct("RUN-101");
        assertEquals("RUN-101", product.getSku());
        assertEquals("Running Shoes", product.getName());
        assertEquals(120.50, product.getPrice());
        assertTrue(product.isAvailability());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> client.getProduct("UNKNOWN"));
        assertEquals("Product not found", exception.getMessage());
    }
}
