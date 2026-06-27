package ust.gama.sdet.Gate4.PactContract.contracts.store;

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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt .class)
@PactTestFor(providerName = "catalog-service", pactVersion = PactSpecVersion.V4)
public class StoreFrontWebNegativePactTest {

//    FETCHING NON EXISTENT ORDER

    @Pact(provider = "catalog-service", consumer = "storefront-web")
    V4Pact fetchingNonExistentOrder(PactDslWithProvider builder){
        return builder
                .given("Order Id 999 does not exist")
                .uponReceiving("a request for non-existent order")
                .path("/catalog/sku/999")
                .method("GET")
                .willRespondWith()
                .status(404)
                .matchHeader("Content-Type",
                        "application/json(;.*)?",
                        "application/json")
                .body(new PactDslJsonBody()
                        .stringType("error", "ORDER_NOT_FOUND")
                        .stringType("message", "Order not Found"))

                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "fetchingNonExistentOrder")
    void testForFetchingNonExistentOrder(MockServer mockServer){
        StoreFront store =new StoreFront(mockServer.getUrl());
        StoreFront.ErrorMessage response =store.getNegOrder(999);

        assertEquals("ORDER_NOT_FOUND", response.error());
        assertEquals("Order not Found", response.message());
        assertEquals(404, response.statusCode());
    }
}
