package tests;

import base.BaseTest;
import models.OrderRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDbValidationTest extends BaseTest {

    @Test
    void orderShouldExistInDatabase() throws Exception {

        long orderId = 1;

        assertTrue(
                dbSupport.orderExists(orderId)
        );
    }

    @Test
    void orderShouldMatchDatabaseValues() throws Exception {

        long orderId = 1;

        OrderRow order =
                dbSupport.findOrder(orderId);

        assertNotNull(order);

        assertEquals(
                1L,
                order.id()
        );

        assertEquals(
                "ORD-1001",
                order.orderNumber()
        );

        assertEquals(
                "CREATED",
                order.status()
        );

        assertEquals(
                new BigDecimal("999.99"),
                order.total()
        );

        assertEquals(
                "USER001",
                order.userId()
        );
    }

    @Test
    void orderStatusShouldMatch() throws Exception {

        String status =
                dbSupport.findOrderStatus(1);

        assertEquals(
                "CREATED",
                status
        );
    }
}