package com.week4consumer;

    
import au.com.dius.pact.consumer.MockServer;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import io.restassured.response.Response;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.mysql.cj.x.protobuf.MysqlxCrud.Order;

import static io.restassured.RestAssured.given;
 
@ExtendWith(PactConsumerTestExt.class)

@PactTestFor(providerName = "retail-app",pactVersion = PactSpecVersion.V4)

public class PactCreated {
 
    @Pact(provider = "retail-app", consumer = "users-consumers")
    V4Pact getProduct(PactDslWithProvider builder) {
        return builder
                .given("Product 101 exists")
                .uponReceiving("Product is present")
                .path("/product/101")
                .method("GET")
                .willRespondWith()
                .status(200)
                .matchHeader("Content-Type", "application/json(;.*)?", "application/json")
                .body(new PactDslJsonBody()
                        .integerType("productId", 101)
                        .stringType("name", "Water Bottle")
                        .numberType("price", 2000.0))
                .toPact(V4Pact.class);
    }
 
 

    @Test
    @PactTestFor(pactMethod = "getProduct")
    void testGetProduct(MockServer mockServer) {
 
        var product = new Client(mockServer.getUrl()).getProduct(101);
        assertEquals(101, product.productId());
        assertEquals("Water Bottle", product.name());
        assertEquals(2000.0, product.price(),0);
 
    }

}
