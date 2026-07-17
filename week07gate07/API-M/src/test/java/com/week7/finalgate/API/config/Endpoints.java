package com.week7.finalgate.API.config;

public final class Endpoints {

    private Endpoints() {}

    // AUTH

    public static final String LOGIN =
            "/auth/login";

    public static final String ME =
            "/auth/me";

    public static final String ADMIN_PING =
            "/auth/admin-ping";

    // FLIGHTS

    public static final String FLIGHTS =
            "/flights";

    public static final String FLIGHT_SEATS =
            "/flights/%s/seats";

    // BOOKINGS

    public static final String BOOKINGS =
            "/bookings";

    public static final String PAY =
            "/bookings/%s/pay";

    public static final String CONFIRM =
            "/bookings/%s/confirm";

    public static final String CANCEL =
            "/bookings/%s/cancel";

    public static final String BOOKING_BY_PNR =
            "/bookings/%s";

    // OPS

    public static final String RESET =
            "/reset";
}