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
public class InventoryTest extends InventoryPact {

    @Test
    @PactTestFor(pactMethod = "getInventory")
    void testInventory(MockServer mockServer) {

        OmsClient client = new OmsClient(mockServer.getUrl());

        OmsClient.Inventory inventory = client.getInventory("SKU-9");

        assertEquals(200, inventory.statuscode());
        assertEquals("SKU-9", inventory.sku());
        assertEquals(20, inventory.quantity());
    }
}
