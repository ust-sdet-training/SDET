package com.tripstack.tests.bookings;

import com.tripstack.base.BaseTest;
import com.tripstack.utils.TokenManager;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PaymentTest extends BaseTest {

    @Test
    public void verifyPaymentFlow() {

        String token = TokenManager.getToken();

        Response bookingResponse =
                bookingClient.createBooking(
                        token,
                        "bus",
                        "BUS-IXCBLR-1",
                        List.of("L3"),
                        true
                );

        String bookingId =
                bookingResponse.jsonPath()
                        .getString("id");

        Response paymentResponse =
                bookingClient.payBooking(
                        token,
                        bookingId
                );

        System.out.println(
                "Payment Status Code = "
                        + paymentResponse.statusCode()
        );

        paymentResponse.prettyPrint();
    }
}