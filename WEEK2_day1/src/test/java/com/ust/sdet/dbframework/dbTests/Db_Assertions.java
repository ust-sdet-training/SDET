package com.ust.sdet.dbframework.dbTests;

import com.ust.sdet.dbframework.model.OrderRow;

import static org.junit.jupiter.api.Assertions.*;

public final class Db_Assertions {

    private Db_Assertions() {
    }

    public static void OrderExistsorNot(OrderRow order) {

        assertNotNull(
                order,
                "Order was not found in database"
        );
    }

    public static void OrderStatus(
            OrderRow order,
            String expectedStatus) {

        assertEquals(
                expectedStatus,
                order.status(),
                "Order status mismatch"
        );
    }

    public static void OrderNumber(
            OrderRow order,
            String expectedOrderNumber) {

        assertEquals(
                expectedOrderNumber,
                order.orderNumber(),
                "Order number mismatch"
        );
    }
}