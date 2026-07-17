package com.api.stepdefs;

import com.api.clients.BookingClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

public class BookingStep {

    private final BookingClient bookingClient;

    public BookingStep() {
        bookingClient = new BookingClient();
    }

    @Step("Create booking")
    public Response createBooking(String token,
                                  String journeyType,
                                  String inventoryId,
                                  List<String> seatIds,
                                  boolean refundable,
                                  int holdTtlSec) {

        return bookingClient.createBooking(
                token,
                journeyType,
                inventoryId,
                seatIds,
                refundable,
                holdTtlSec
        );
    }

    @Step("Pay booking")
    public Response payBooking(String token, String bookingId) {
        return bookingClient.payBooking(token, bookingId);
    }

    @Step("Confirm booking")
    public Response confirmBooking(String token, String bookingId) {
        return bookingClient.confirmBooking(token, bookingId);
    }

    @Step("Cancel booking")
    public Response cancelBooking(String token, String bookingId) {
        return bookingClient.cancelBooking(token, bookingId);
    }

    @Step("Get bookings")
    public Response getBookings(String token) {
        return bookingClient.getBookings(token);
    }

    @Step("Get booking by PNR")
    public Response getBookingByPnr(String token, String pnr) {
        return bookingClient.getBookingByPnr(token, pnr);
    }

    @Step("Get booking id from create booking response")
    public String getBookingId(Response response) {
        return response.jsonPath().getString("id");
    }

    @Step("Get booking pnr")
    public String getPnr(Response response) {
        return response.jsonPath().getString("pnr");
    }
}