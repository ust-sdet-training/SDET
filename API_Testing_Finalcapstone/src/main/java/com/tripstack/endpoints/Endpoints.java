package com.tripstack.endpoints;

public final class Endpoints {

    private Endpoints() {
    }

    public static final String LOGIN = "/auth/login";
    public static final String ME = "/auth/me";
    public static final String SEARCH_FLIGHTS = "/flights";
    public static final String SEAT_MAP = "/flights/%s/seats";
    public static final String BOOKINGS = "/bookings";
    public static final String BOOKING_BY_PNR = "/bookings/%s";
    public static final String PAYMENT = "/bookings/%s/pay";
    public static final String CONFIRM = "/bookings/%s/confirm";
    public static final String CANCEL = "/bookings/%s/cancel";
}
