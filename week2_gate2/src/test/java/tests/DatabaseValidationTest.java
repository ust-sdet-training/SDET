package tests;

import base.BaseTest;
import models.OrderRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseValidationTest extends BaseTest {

    private static final long ORDER_ID = 1;

    @Test
    void databaseShouldBeReachable() throws Exception {

        assertTrue(
                dbSupport.isReachable()
        );
    }

    @Test
    void orderShouldExistInDatabase() throws Exception {

        assertTrue(
                dbSupport.orderExists(ORDER_ID)
        );
    }

    @Test
    void orderShouldBeFetchedFromDatabase() throws Exception {

        OrderRow order =
                dbSupport.findOrder(ORDER_ID);

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
    void orderStatusShouldMatchDatabase() throws Exception {

        assertEquals(
                "CREATED",
                dbSupport.findOrderStatus(ORDER_ID)
        );
    }
}