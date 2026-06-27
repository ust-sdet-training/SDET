package ust.gama.sdet.Gate4.PactContract.contracts.store;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "catalog-service", pactVersion = PactSpecVersion.V4)
public class StoreFrontWebPactTest {

    //STOREFRONT-WEB 'GET's ORDER FROM CATALOG

    @Pact(provider = "catalog-service", consumer = "storefront-web")
    V4Pact fetchingOrderFromCatalog(PactDslWithProvider builder){
        return builder
                .given("order ID 84 exists")
                .uponReceiving("a request for order 84")
                .path("/catalog/sku/84")
                .method("GET")
                .willRespondWith()
                .status(200)
                .matchHeader("Content-Type",
                             "application/json(;.*)?",
                             "application/json")
                .body(new PactDslJsonBody()
                        .integerType("orderId", 84)
                        .stringType("product", "TravelBag")
                        .stringType("status", "CONFIRMED")
                        .numberType("totalPrice", 93.0))
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "fetchingOrderFromCatalog")
    void testForFetchingOrderSKU84(MockServer mockServer){
        StoreFront store =new StoreFront(mockServer.getUrl());
        StoreFront.Order response =store.fetchOrder(84);

        assertEquals(84, response.orderId());
        assertEquals("TravelBag", response.product());
        assertEquals("CONFIRMED", response.status());
        assertEquals(93.0, response.totalPrice());
    }

    //STOREFRONT-WEB 'POST's TO CATALOG FOR CREATING ORDER

    @Pact(provider = "catalog-service", consumer = "storefront-web")
    V4Pact creatingOrderByUsingPost(PactDslWithProvider builder){
        return builder
                .given("Catalog Service can create Orders")
                .uponReceiving("a request to create order")
                .path("/catalog/orders")
                .method("POST")
                .matchHeader("Content-Type",
                        "application/json(;.*)?",
                        "application/json")
                .body(new PactDslJsonBody()
                        .integerType("statusCode", 0)
                        .integerType("orderId", 104)
                        .stringType("product", "YogaMat")
                        .stringType("status", "NEW")
                        .numberType("totalPrice", 240.0))
                .willRespondWith()
                .status(201)
                .matchHeader("Content-Type",
                        "application/json(;.*)?",
                        "application/json")
                .body(new PactDslJsonBody()
                        .integerType("statusCode", 201)
                        .integerType("orderId", 104)
                        .stringType("product", "YogaMat")
                        .stringType("status", "CREATED")
                        .numberType("totalPrice", 240.0))
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "creatingOrderByUsingPost")
    void testForPostingOrderToCatalog(MockServer mockServer){
        StoreFront store =new StoreFront(mockServer.getUrl());
        StoreFront.Order request =new StoreFront.Order(
                0,
                104,
                "YogaMat",
                "NEW",
                240.0
        );
        StoreFront.Order response =store.createOrder(request);

        assertEquals(104, response.orderId());
        assertEquals("YogaMat", response.product());
        assertEquals("CREATED", response.status());
        assertEquals(240.0, response.totalPrice());
    }

    //STOREFRONT-WEB 'GET' TEST IS DISABLED DUE TO CIRCUMSTANCES

    @Disabled("Catalog-Service is temporarily down due to maintenance")
    @Pact(provider = "catalog-service", consumer = "storefront-web")
    V4Pact disabledGetOrderPact(PactDslWithProvider builder){
        return builder
                .given("Catalog Service is down")
                .uponReceiving("a request for order when service is down")
                .path("/catalog/orders/84")
                .method("GET")
                .willRespondWith()
                .status(503)
                .matchHeader("Content-Type",
                        "application/json(;.*)?",
                        "application/json")
                .
                body(new PactDslJsonBody()
                        .stringType("reason", "SERVICE_IS_DOWN")
                        .stringType("message", "Catalog service is down temporality." +
                                "Sorry for the delay, and thank you for your Patience!! "))
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "disabledGetOrderPact")
    void testForDisabledPact(MockServer mockServer){
        StoreFront store =new StoreFront(mockServer.getUrl());
        StoreFront.InfoMessage response =store.getInfoMessage(84);

        assertTrue(response.reason().contains("SERVICE_IS_DOWN"));
        assertTrue(response.message().contains("Catalog service is down temporality"));
        assertEquals(503, response.statusCode());
    }

}
