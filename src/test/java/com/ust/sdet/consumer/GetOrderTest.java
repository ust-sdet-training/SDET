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
class GetOrderTest extends GetOrderPact{

    @Test
    @PactTestFor(pactMethod = "pact")
    void test(MockServer mockServer) {

        OmsClient client = new OmsClient(mockServer.getUrl());
        var order = client.getOrder(12);

        assertEquals(200, order.statuscode());
        assertEquals(12, order.orderId());
    }
}
