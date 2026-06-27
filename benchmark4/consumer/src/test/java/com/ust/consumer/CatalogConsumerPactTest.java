package com.ust.consumer;
import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.V4Pact;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "catalog-service")
public class CatalogConsumerPactTest {

    @Pact(provider = "catalog-service",
            consumer = "storefront-web")
    public V4Pact createPact(PactBuilder builder) {

        return builder
                .given("SKU-1 exists")
                .expectsToReceiveHttpInteraction("Get Product", http -> http
                        .withRequest(request -> request
                                .method("GET")
                                .path("/catalog/SKU-1"))
                        .willRespondWith(response -> response
                                .status(200)
                                .body(new PactDslJsonBody()
                                        .integerType("priceMinor", 129900)
                                        .stringValue("availability", "IN_STOCK"))))
                .given("SKU-404 missing")
                .expectsToReceiveHttpInteraction("Get Missing Product", http -> http
                        .withRequest(request -> request
                                .method("GET")
                                .path("/catalog/SKU-404"))
                        .willRespondWith(response -> response
                                .status(404)))
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createPact")
    void testConsumer(MockServer mockServer) {

        given()
                .baseUri(mockServer.getUrl())
                .when()
                .get("/catalog/SKU-1")
                .then()
                .statusCode(200);

        given()
                .baseUri(mockServer.getUrl())
                .when()
                .get("/catalog/SKU-404")
                .then()
                .statusCode(404);
    }
}
