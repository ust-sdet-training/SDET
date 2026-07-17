package com.tripstack.tests.bookings;

import com.tripstack.base.BaseTest;
import com.tripstack.utils.TokenManager;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class ConfirmBookingTest extends BaseTest {

    @Test
    public void verifyBookingConfirmation() {

        String token =
                TokenManager.getToken();

        String bookingId =
                "<BOOKING_ID>";

        Response response =
                bookingClient.confirmBooking(
                        token,
                        bookingId
                );

        response.prettyPrint();
    }
}