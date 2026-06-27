package com.ust.sdet.consumer;


import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import com.ust.sdet.OmsClient;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Tag("consumer")
@ExtendWith(PactConsumerTestExt.class)
class InvalidOrderTest extends GetOrderPact{

//    @Test
//    @PactTestFor(pactMethod = "pact")
//    void test(MockServer mockServer) {
//
//        OmsClient client = new OmsClient(mockServer.getUrl());
//        var order = client.getinvalidOrder(1234);
//
//
//    }
}
