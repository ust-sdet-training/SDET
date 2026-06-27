package com.ust.sdet.POS;

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

public class POSConsumerVerification {


//    Pact 1
@Pact(provider="OMS-Provider", consumer = "POS-Provider")
    public V4Pact createAPactForNewOrder(PactDslWithProvider builder){

    return builder
            .given("Create a New Order with ID 101")
            .uponReceiving("Create Order")
            .path("/orders")
            .method("POST")
            .willRespondWith()
            .status(201)
            .body(new PactDslJsonBody()
                    .integerType("id",101)
                    .stringType("category","Mobiles")
                    .integerType("stock", 100)
                    .stringType("product_name","Samsung s26")
                    .stringType("status","CREATED")
            )
            .toPact(V4Pact.class);
}

    @Test
    @PactTestFor(pactMethod = "createAPactForNewOrder")
    void verifyCreatedOrder(MockServer mockServer) throws IOException {

        given()
                .baseUri(mockServer.getUrl())
                .when()
                .post("/orders")
                .then()
                .statusCode(201)
                .body("id", equalTo(101))
                .body("category",equalTo("Mobiles"))
                .body("product_name",equalTo("Samsung s26"))
                .body("status", equalTo("CREATED"));
    }


//    Pact 2
    @Pact(provider="OMS-Provider", consumer = "POS-Provider")
    public V4Pact checkForExistingOrder(PactDslWithProvider builder){

        return builder
                .given("Check the order with ID 101 exists")
                .uponReceiving("Check Order Exists")
                .path("/orders/101")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody()
                        .integerType("id",101)
                        .stringType("category","Mobiles")
                        .integerType("stock", 100)
                        .stringType("product_name","Samsung s26")
                )
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "checkForExistingOrder")
    void verifyExistingOrder(MockServer mockServer) throws IOException {

        given()
                .baseUri(mockServer.getUrl())
                .when()
                .get("/orders/101")
                .then()
                .statusCode(200)
                .body("id", equalTo(101))
                .body("category",equalTo("Mobiles"))
                .body("stock",equalTo(100));
    }




//    Pact 3

    @Pact(provider = "OMS-Provider", consumer = "POS-Consumer")
    public V4Pact CheckInventoryPact(PactDslWithProvider builder) {

        return builder
                .given("Check the inventory for Mobile")
                .uponReceiving("Check Inventory")
                .path("/inventory/Mobiles")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody()
                        .stringType("mobile", "Samsung")
                        .integerType("stock", 60)
                        .stringType("address","India")
                )
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "CheckInventoryPact")
    void verifyInventory(MockServer mockServer) throws IOException {

        given()
                .baseUri(mockServer.getUrl())
                .when()
                .get("/inventory/Mobiles")
                .then()
                .statusCode(200)
                .body("mobile", equalTo("Samsung"))
                .body("address",equalTo("India"))
                .body("stock",equalTo(60));
    }




}
