package com.travelbooking.tests.payment;

import com.travelbooking.base.BaseTest;
import com.travelbooking.models.request.HoldRequest;
import com.travelbooking.models.request.PaymentRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PaymentApiTest extends BaseTest {

    @Test
    void successfulPayment() {

        Response searchResponse = searchClient.searchBuses(
                "LKO",
                "BLR",
                "2026-08-12",
                token
        );

        assertEquals(200, searchResponse.getStatusCode());

        // Verify the correct field from the search response.
        String inventoryId = searchResponse.jsonPath().getString("buses[0].id");
        assertNotNull(inventoryId);

        HoldRequest holdRequest = new HoldRequest();
        holdRequest.setJourneyType("bus");
        holdRequest.setInventoryId(inventoryId);
        holdRequest.setSeatIds(List.of("L7"));

        Response holdResponse = bookingClient.holdBooking(
                holdRequest,
                token
        );

        System.out.println("Hold Response:");
        System.out.println(holdResponse.asPrettyString());

        assertEquals(201, holdResponse.getStatusCode());

        String bookingId = holdResponse.jsonPath().getString("id");
        assertNotNull(bookingId);

        PaymentRequest paymentRequest = new PaymentRequest("CARD");

        Response paymentResponse = paymentClient.pay(
                bookingId,
                paymentRequest,
                token
        );

        System.out.println("Payment Response:");
        System.out.println(paymentResponse.asPrettyString());

        assertEquals(200, paymentResponse.getStatusCode());

        Response confirmResponse = bookingClient.confirmBooking(
                bookingId,
                token
        );

        System.out.println("Confirm Status: " + confirmResponse.getStatusCode());
        System.out.println(confirmResponse.asPrettyString());

        assertEquals(200, confirmResponse.getStatusCode());
    }
}