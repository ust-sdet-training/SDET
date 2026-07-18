package com.travelbooking.tests.database;

import com.travelbooking.database.models.Payment;
import com.travelbooking.database.queries.PaymentQueries;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentDatabaseTest {

    @Test
    void shouldValidatePaymentDetails() {

        PaymentQueries paymentQueries = new PaymentQueries();

        Payment payment = paymentQueries.getPaymentByBookingId(1);

        assertNotNull(payment);
        assertEquals("UPI", payment.getPaymentMethod());
        assertEquals("SUCCESS", payment.getPaymentStatus());
        assertEquals(2500.00, payment.getAmount().doubleValue());
    }
}