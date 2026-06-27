package com.ust.sdet.wiremock.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;

import io.restassured.RestAssured;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "Product")
public class ConsumerTest {

    @Pact(provider = "Product",
            consumer = "Retail")
    public V4Pact productExists(PactDslWithProvider builder) {

        PactDslJsonBody responseBody =
                new PactDslJsonBody()
                        .integerType("id", 101)
                        .stringType("name", "Travel Backpack")
                        .integerType("price", 89)
                        .stringType("stock", "In Stock");

        return builder
                .given("product exists")
                .uponReceiving("get product details")
                .path("/products/101")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(responseBody)
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "productExists")
    void verifyProduct(MockServer mockServer) {

        RestAssured
                .given()
                .baseUri(mockServer.getUrl())
                .when()
                .get("/products/101")
                .then()
                .statusCode(200)
                .body("id", equalTo(101))
                .body("name", equalTo("Travel Backpack"))
                .body("price", equalTo(89))
                .body("stock", equalTo("In Stock"));
    }
}