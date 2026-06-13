package com.ust.sdet.api.DbFramework.Assertions;

import com.ust.sdet.api.DbFramework.Model.OrderRow;

import static org.junit.jupiter.api.Assertions.*;

public final class DbAssertions {

    private DbAssertions() {
    }

    public static void assertOrderExists(OrderRow order) {

        assertNotNull(
                order,
                "Order was not found in database"
        );
    }

    public static void assertOrderStatus(
            OrderRow order,
            String expectedStatus) {

        assertEquals(
                expectedStatus,
                order.status(),
                "Order status mismatch"
        );
    }

    public static void assertOrderNumber(
            OrderRow order,
            String expectedOrderNumber) {

        assertEquals(
                expectedOrderNumber,
                order.orderNumber(),
                "Order number mismatch"
        );
    }
}