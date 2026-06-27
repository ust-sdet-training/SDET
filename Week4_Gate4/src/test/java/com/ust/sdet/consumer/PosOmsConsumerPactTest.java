package com.ust.sdet.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;

import io.restassured.response.Response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(
    providerName = "oms-provider",
    pactVersion = PactSpecVersion.V4
)
public class PosOmsConsumerPactTest {
    @Pact(provider = "oms-provider", consumer = "pos-consumer")
    V4Pact getOrder(PactDslWithProvider builder) {
        return builder
                .given("Order 123 exists")
                .uponReceiving("a request for order 123")
                .path("/order/123")
                .method("GET")
                .willRespondWith()
                .status(200)
                .matchHeader(
                    "Content-Type",
                    "application/json(;.*)?",
                    "application/json"
                )
                .body(new PactDslJsonBody()
                    .integerType("orderId", 123)
                    .stringType("status", "CONFIRMED")
                    .numberType("total", 42.0)
                )
                .toPact(V4Pact.class);
    }
    @Test
    @PactTestFor(pactMethod = "getOrder")
    void testGetOrder(MockServer mockServer) {
        Response response =
                given()
                .baseUri(mockServer.getUrl())
                .when()
                .get("/order/123");

        response.then().statusCode(200);

        response.then().log().all();
    }
}
