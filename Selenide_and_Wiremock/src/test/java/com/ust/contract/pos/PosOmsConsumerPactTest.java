package com.ust.contract.pos;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(
        providerName = "oms-provider",
        pactVersion = PactSpecVersion.V4
)
@Tag("contract")
public class PosOmsConsumerPactTest {

    @Pact(provider = "oms-provider", consumer = "pos-consumer")
    V4Pact getMissing404(PactDslWithProvider builder) {

        return builder
                .given("Order 203 not exists")
                .uponReceiving("a request for missing order 203")
                .path("/order/203")
                .method("GET")
                .willRespondWith()
                .status(404)
                .matchHeader("Content-Type",
                        "application/json(;.*)?",
                        "application/json")
                .body(new PactDslJsonBody()
                        .stringType("status", "Product not found"))
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "getMissing404")
    void testGetMissing(MockServer mockServer) {

        Response response =
                new OmsClient(mockServer.getUrl()).get404(203);

        assertEquals(404, response.statusCode());
        assertEquals("Product not found",
                response.jsonPath().getString("status"));
    }

    @Pact(provider = "oms-provider", consumer = "pos-consumer")
    V4Pact getOrder200(PactDslWithProvider builder) {

        return builder
                .given("Order 123 exists")
                .uponReceiving("a request for order 123")
                .path("/order/123")
                .method("GET")
                .willRespondWith()
                .status(200)
                .matchHeader("Content-Type",
                        "application/json(;.*)?",
                        "application/json")
                .body(new PactDslJsonBody()
                        .integerType("orderId", 123)
                        .stringType("status", "CONFIRMED")
                        .numberType("total", 42.0))
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "getOrder200")
    void testGetOrder(MockServer mockServer) {

        var order =
                new OmsClient(mockServer.getUrl()).get200(123);

        assertEquals(123, order.orderId());
        assertEquals("CONFIRMED", order.status());
        assertEquals(42.0, order.total(), 0);
    }
}