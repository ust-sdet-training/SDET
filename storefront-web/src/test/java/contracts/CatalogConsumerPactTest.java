package contract;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;

import client.CatalogClient;
import config.ConsConfig;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "catalog-service", pactVersion = PactSpecVersion.V4)
public class CatalogConsumerPactTest {
    @Pact(provider = "catalog-service", consumer = "storefront-web")
    V4Pact skuExists(PactDslWithProvider builder) {
        return builder
                .given("SKU-1 exists")
                .uponReceiving("get catalog item SKU-1")
                .path("/catalog/SKU-1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .matchHeader("Content-Type", "application/json(;.*)?", "application/json")
                .body(new PactDslJsonBody()
                                .stringType("sku", "SKU-1")
                                .stringType("name", "Running Shoes")
                                .integerType("priceMinor", 129980)
                                .stringMatcher(
                                        "availability",
                                        "IN_STOCK|LOW|OUT_OF_STOCK",
                                        "IN_STOCK"
                                )
                )
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "skuExists")
    void testSkuExists(MockServer mockServer) {
        var response = new CatalogClient(mockServer.getUrl()).getCatalogItem("SKU-1");

        assertEquals(200, response.statusCode());
        assertEquals("SKU-1", response.path("sku"));
        assertEquals("Running Shoes", response.path("name"));
        assertEquals("IN_STOCK", response.path("availability"));
    }

    @Pact(provider = "catalog-service", consumer = "storefront-web")
    V4Pact skuMissing(PactDslWithProvider builder) {
        return builder
                .given("SKU-404 missing")
                .uponReceiving("get catalog item SKU-404")
                .path("/catalog/SKU-404")
                .method("GET")
                .willRespondWith()
                .status(404)
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "skuMissing")
    void testSkuMissing(MockServer mockServer) {
        var response = new CatalogClient(mockServer.getUrl()).getCatalogItem("SKU-404");
        assertEquals(404, response.statusCode());
    }
}