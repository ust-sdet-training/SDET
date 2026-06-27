package com.week4.provider;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProviderPactTest {

    @Test
    void verifyProvider() {
        String response = CatalogController.getProduct("SKU-1");

        assertTrue(response.contains("IN_STOCK"));
        assertTrue(response.contains("129900"));
    }
}