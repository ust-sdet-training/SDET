package com.travelbooking.tests.booking;

import com.travelbooking.base.BaseTest;
import com.travelbooking.models.request.HoldRequest;
import com.travelbooking.models.request.PaymentRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookingApiTest extends BaseTest {

    @Test
    void completeBookingFlow() {

        Response searchResponse = searchClient.searchBuses(
                "LKO",
                "BLR",
                "2026-08-12",
                token
        );

        assertEquals(200, searchResponse.getStatusCode());

        String inventoryId = searchResponse.jsonPath().getString("buses[0].id");
        assertNotNull(inventoryId);

        HoldRequest holdRequest = new HoldRequest(
                "bus",
                inventoryId,
                List.of("L1"),
                true,
                300
        );

        Response holdResponse = bookingClient.holdBooking(holdRequest, token);


        assertEquals(201, holdResponse.getStatusCode());

        String bookingId = holdResponse.jsonPath().getString("id");
        assertNotNull(bookingId);

        PaymentRequest paymentRequest = new PaymentRequest("CARD");

        Response paymentResponse = paymentClient.pay(
                bookingId,
                paymentRequest,
                token
        );

        assertEquals(200, paymentResponse.getStatusCode());


        Response confirmResponse = bookingClient.confirmBooking(
                bookingId,
                token
        );

        assertEquals(200, confirmResponse.getStatusCode());

        Response bookingsResponse = bookingClient.getMyBookings(token);

        assertEquals(200, bookingsResponse.getStatusCode());
    }

    @Test
    void cancelBooking() {

        Response searchResponse = searchClient.searchBuses(
                "LKO",
                "BLR",
                "2026-08-12",
                token
        );

        assertEquals(200, searchResponse.getStatusCode());

        String inventoryId = searchResponse.jsonPath().getString("buses[0].id");
        assertNotNull(inventoryId);

        HoldRequest holdRequest = new HoldRequest();
        holdRequest.setJourneyType("bus");
        holdRequest.setInventoryId(inventoryId);
        holdRequest.setSeatIds(List.of("L1"));

        Response holdResponse = bookingClient.holdBooking(
                holdRequest,
                token
        );

        assertEquals(201, holdResponse.getStatusCode());

        String bookingId = holdResponse.jsonPath().getString("id");
        assertNotNull(bookingId);

        Response cancelResponse = bookingClient.cancelBooking(
                bookingId,
                token
        );

        assertEquals(200, cancelResponse.getStatusCode());
    }
}