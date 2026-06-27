package com.api.sdet.consumer;

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

import java.io.IOException;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(pactVersion = PactSpecVersion.V4)
public class CatalogServiceConsumerTest {

    @Pact(provider = "catalog-provider", consumer = "catalog-consumer")
    public V4Pact getItemPact(PactDslWithProvider builder) {

        return builder
                .given("Item 9 exists")
                .uponReceiving("Get item 9")
                .path("/catalog/9")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody()
                        .integerType("id", 9)
                        .integerType("priceMinor", 10000)
                        .stringMatcher("availability","IN_STOCK|LOW_STOCK|OUT_OF_STOCK","IN_STOCK"))
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "getItemPact")
    void verifyItem(MockServer mockServer) throws IOException {

        given()
                .baseUri(mockServer.getUrl())
                .when()
                .get("/catalog/9")
                .then()
                .statusCode(200)
                .log().all()
                .body("id", equalTo(9))
                .body("priceMinor",equalTo(10000))
                .body("availability", equalTo("IN_STOCK"));
    }

    @Pact(provider = "catalog-provider", consumer = "catalog-consumer")
    public V4Pact getWrongItemPact(PactDslWithProvider builder) {

        return builder
                .given("Item 1000 does not exist")
                .uponReceiving("Get item 1000")
                .path("/catalog/1000")
                .method("GET")
                .willRespondWith()
                .status(404)
                .body(new PactDslJsonBody()
                        .stringType("error", "Product not found"))
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "getWrongItemPact")
    void verifyWrongItem(MockServer mockServer) throws IOException {

        given()
                .baseUri(mockServer.getUrl())
                .when()
                .get("/catalog/1000")
                .then()
                .statusCode(404)
                .log().all()
                .body("error",equalTo("Product not found"));
    }

}
