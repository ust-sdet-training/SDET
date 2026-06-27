package com.ust.sdet.consumer;


import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import com.ust.sdet.OmsClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;


import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("consumer")
@ExtendWith(PactConsumerTestExt.class)
public class CreateOrderTest extends CreateOrderPact {

    @Test
    @PactTestFor(pactMethod = "createOrder")
    public void testCreateOrder(MockServer mockServer) {

        OmsClient client = new OmsClient(mockServer.getUrl());

        OmsClient.Order order = client.createOrder("SKU-9", 20);

        assertEquals(201, order.statuscode());
        assertEquals("CREATED", order.status());
        assertEquals(2000.0, order.total());
    }
}
