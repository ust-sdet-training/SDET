package com.travelbooking.clients;

import io.restassured.response.Response;

public class BookingClient extends BaseClient {

    private static final String BOOKINGS = "/api/bookings";

    public Response holdBooking(Object request, String token) {
        return post(BOOKINGS, request, token);
    }

    public Response confirmBooking(String bookingId, String token) {
        return post(BOOKINGS + "/" + bookingId + "/confirm", token);
    }

    public Response cancelBooking(String bookingId, String token) {
        return post(BOOKINGS + "/" + bookingId + "/cancel", token);
    }

    public Response getMyBookings(String token) {
        return get(BOOKINGS, token);
    }

    public Response getBookingByPnr(String pnr, String token) {
        return get(BOOKINGS + "/" + pnr, token);
    }
}