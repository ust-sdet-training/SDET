package com.week4_gate4.contractpact.strorefront;

import au.com.dius.pact.consumer.MockServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(
        providerName = "catalog",
        pactVersion = PactSpecVersion.V4
)
public class StoreFrontPactTest
{
    @Pact(
            provider = "catalog",
            consumer = "storefront"
    )
    V4Pact getProduct(PactDslWithProvider builder) {
        return builder
                .given("Product RunningShoe is in catalogPage")
                .uponReceiving("a request for RunningShoe")
                .path("/product/106")
                .method("GET")
                .willRespondWith()
                .status(200)
                .matchHeader(
                        "Content-Type",
                        "application/json(;.*)?",
                        "application/json"
                )
                .body(new PactDslJsonBody()
                        .integerType("id", 106)
                        .stringType("status", "in_stock")
                        .numberType("total", 4500)
                )
                .toPact(V4Pact.class);
    }
        @Test
        @PactTestFor(pactMethod = "getProduct")
        void testGetOrder(MockServer mockServer) {


            SfClient client =  new SfClient(mockServer.getUrl());


            SfClient.Order order =
                    client.getOrder(106);


            assertEquals(200, order.statuscode());
            assertEquals(106, order.orderId());
            assertEquals("in_stock", order.status());
            assertEquals(4500, order.total());

        }
        //second patch

    @Pact(
            provider = "catalog",
            consumer = "storefront"
    )
    V4Pact checkInv(PactDslWithProvider builder) {
        return builder
                .given("Sku-9 has stock in cart")

                .uponReceiving("a request for Sku-9")
                .path("/cart/106")
                .method("GET")

                .willRespondWith()
                .status(200)

                .matchHeader(
                        "Content-Type",
                        "application/json(;.*)?",
                        "application/json"
                )

                .body(new PactDslJsonBody()
                        .integerType("id", 106)
                        .stringType("status", "Confirmed")
                        .numberType("total", 4500)
                )

                .toPact(V4Pact.class);
    }
    @Test
    @PactTestFor(pactMethod = "checkInv")
    void checktheinventory(MockServer mockServer) {


        SfClient client =  new SfClient(mockServer.getUrl());


        SfClient.Order order =
                client.getInventory(106);


        assertEquals(200, order.statuscode());
        assertEquals(106, order.orderId());
        assertEquals("Confirmed", order.status());
        assertEquals(4500, order.total());

    }
        //third patch
        @Pact(
                provider = "catalog",
                consumer = "storefront"
        )
        V4Pact createOrder(PactDslWithProvider builder) {

            return builder

                    .given("Running Shoe Added to the cart")

                    .uponReceiving("request for add to cart")
                    .path("/items/106")
                    .method("POST")

                    .matchHeader(
                            "Content-Type",
                            "application/json(;.*)?",
                            "application/json"
                    )

                    .body(new PactDslJsonBody()
                            .stringType("sku", "SKU-9")
                            .integerType("quantity", 1)
                    )

                    .willRespondWith()
                    .status(201)

                    .matchHeader(
                            "Content-Type",
                            "application/json(;.*)?",
                            "application/json"
                    )

                    .body(new PactDslJsonBody()
                            .stringType("sku", "SKU-9")
                            .integerType("quantity", 1)
                    )

                    .toPact(V4Pact.class);
        }
    @Test
    @PactTestFor(pactMethod = "createOrder")
    void testCreateOrder(MockServer mockServer) {


        SfClient client =
                new SfClient(mockServer.getUrl());


        SfClient.CreateOrder order =
                client.createOrder(
                        "SKU-9",
                        1
                );


        assertEquals("SKU-9", order.sku());
        assertEquals(1, order.quantity());

    }

}
