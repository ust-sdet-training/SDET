package com.tripstack.client;

import java.util.Collections;
import java.util.Map;

import com.tripstack.core.BaseApiClient;
import com.tripstack.endpoints.Endpoints;
import com.tripstack.model.BookingRequest;

import io.restassured.response.Response;

public class BookingClient extends BaseApiClient {

    public Response createBooking(String token, BookingRequest request) {
        return post(Endpoints.BOOKINGS, request, token);
    }

    public Response payBooking(String token, String bookingId) {
        String endpoint = String.format(Endpoints.PAYMENT, bookingId);
        return post(endpoint, emptyPayload(), token);
    }

    public Response confirmBooking(String token, String bookingId) {
        String endpoint = String.format(Endpoints.CONFIRM, bookingId);
        return post(endpoint, emptyPayload(), token);
    }

    public Response cancelBooking(String token, String bookingId) {
        String endpoint = String.format(Endpoints.CANCEL, bookingId);
        return post(endpoint, emptyPayload(), token);
    }

    private Map<String, Object> emptyPayload() {
        return Collections.emptyMap();
    }

    public Response listBookings(String token) {
        return get(Endpoints.BOOKINGS, token);
    }

    public Response getBookingByPnr(String token, String pnr) {
        String endpoint = String.format(Endpoints.BOOKING_BY_PNR, pnr);
        return get(endpoint, token);
    }
}
