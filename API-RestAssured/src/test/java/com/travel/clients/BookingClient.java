package com.travel.clients;

import com.travel.models.request.BookingRequest;
import com.travel.models.response.BookingResponse;
import com.travel.specs.ResponseSpec;

public class BookingClient extends BaseAPIClient {

    public BookingResponse hold(String token, BookingRequest request) {
        return post("/bookings", request, token).then().spec(ResponseSpec.created()).extract().as(BookingResponse.class);
    }

    public BookingResponse pay(String token, String bookingId) {
        return post("/bookings/" + bookingId + "/pay", "{}", token).then().spec(ResponseSpec.ok()).extract().as(BookingResponse.class);
    }

    public BookingResponse confirm(String token, String bookingId) {
        return post("/bookings/" + bookingId + "/confirm", token).then().spec(ResponseSpec.ok()).extract().as(BookingResponse.class);
    }

    public BookingResponse cancel(String token, String bookingId) {
        return post("/bookings/" + bookingId + "/cancel", token).then().spec(ResponseSpec.ok()).extract().as(BookingResponse.class);
    }

    public BookingResponse byPnr(String token, String pnr) {
        return get("/bookings/" + pnr, token).then().spec(ResponseSpec.ok()).extract().as(BookingResponse.class);
    }
}