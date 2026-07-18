package com.travelbooking.tests.integration;

import com.travelbooking.base.BaseTest;
import com.travelbooking.models.request.HoldRequest;
import com.travelbooking.models.request.PaymentRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingApiDatabaseTest extends BaseTest {

    @Test
    void shouldValidateBookingInApiAndDatabase() {

        Response searchResponse = searchClient.searchBuses(
                "LKO",
                "BLR",
                "2026-08-12",
                token
        );

        assertSuccess(searchResponse);

        String inventoryId = searchResponse.jsonPath().getString("buses[0].id");
        assertNotNull(inventoryId);

        HoldRequest holdRequest = new HoldRequest(
                "bus",
                inventoryId,
                List.of("L6"),
                true,
                300
        );

        Response holdResponse = bookingClient.holdBooking(
                holdRequest,
                token
        );

        assertSuccess(holdResponse);

        String bookingId = holdResponse.jsonPath().getString("id");
        assertNotNull(bookingId);

        Response paymentResponse = paymentClient.pay(
                bookingId,
                new PaymentRequest("CARD"),
                token
        );

        assertSuccess(paymentResponse);

        Response confirmResponse = bookingClient.confirmBooking(
                bookingId,
                token
        );

        assertSuccess(confirmResponse);

        Response bookingsResponse = bookingClient.getMyBookings(token);

        assertSuccess(bookingsResponse);

        String pnr = bookingsResponse.jsonPath().getString("[0].pnr");

        assertNotNull(pnr);
        assertFalse(pnr.isBlank());
    }

    private void assertSuccess(Response response) {

        int statusCode = response.getStatusCode();

        assertTrue(
                statusCode == 200 || statusCode == 201,
                "Expected status code 200 or 201 but got " + statusCode
        );
    }
}