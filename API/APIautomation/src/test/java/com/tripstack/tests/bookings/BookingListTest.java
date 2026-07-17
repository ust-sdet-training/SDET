package com.tripstack.tests.bookings;

import com.tripstack.base.BaseTest;
import com.tripstack.utils.TokenManager;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class BookingListTest extends BaseTest {

    @Test
    public void verifyMyBookings() {

        String token =
                TokenManager.getToken();

        Response response =
                bookingClient.getMyBookings(
                        token
                );

        response.then()
                .statusCode(200);

        response.prettyPrint();
    }
}