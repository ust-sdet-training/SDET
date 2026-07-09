package data;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static data.OrderBuilder.anOrder;
import static org.junit.jupiter.api.Assertions.*;

class BuilderStructureTest {

    @Test
    void builderUsesSensibleDefaults() {

        Order order = anOrder().build();

        assertEquals("SKU-RET-101", order.sku());
        assertEquals(1, order.quantity());
        assertEquals(129900, order.totalPaise());
        assertEquals("NEW", order.status());
        assertEquals(LocalDate.now(), order.orderedOn());
        assertFalse(order.refunded());
    }

    @Test
    void builderStatesOnlyWhatTestCaresAbout() {

        Order order = anOrder()
                .withQuantity(3)
                .build();

        assertEquals(3, order.quantity());

        assertEquals("SKU-RET-101", order.sku());
        assertEquals("NEW", order.status());
        assertFalse(order.refunded());
    }

    @Test
    void refundedBuilderCreatesRefundedOrder() {

        Order order = anOrder().refunded().build();

        assertEquals("REFUNDED", order.status());
        assertTrue(order.refunded());
    }

    @Test
    void invalidBuilderDataFailsBeforeDatabaseSetup() {

        OrderBuilder builder = anOrder().withQuantity(0);

        assertThrows(IllegalArgumentException.class, builder::build);
    }
}