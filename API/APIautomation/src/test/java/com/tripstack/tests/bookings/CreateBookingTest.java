package com.tripstack.tests.bookings;

import com.tripstack.base.BaseTest;
import com.tripstack.utils.TokenManager;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CreateBookingTest extends BaseTest {

    @Test
    public void verifyBookingCreation() {

        String token =
                TokenManager.getToken();

        Response response =
                bookingClient.createBooking(
                        token,
                        "bus",
                        "BUS-IXCBLR-1",
                        List.of("L3"),
                        true
                );

        System.out.println("Status Code = "
                + response.getStatusCode());

        response.prettyPrint();
    }
}