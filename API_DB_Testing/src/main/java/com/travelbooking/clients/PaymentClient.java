package com.travelbooking.clients;

import io.restassured.response.Response;

public class PaymentClient extends BaseClient {

    private static final String BOOKINGS = "/api/bookings";

    public Response pay(String bookingId, Object request, String token) {
        return post(BOOKINGS + "/" + bookingId + "/pay", request, token);
    }
}