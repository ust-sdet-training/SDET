package com.tripstack.constants;

public final class ApiEndpoints {

    private ApiEndpoints() {
    }

    // Authentication
    public static final String LOGIN = "/api/auth/login";

    // Flights
    public static final String FLIGHTS = "/api/flights";
    public static final String FLIGHT_SEATS = "/api/flights/{id}/seats";

    // Bookings
    public static final String BOOKINGS = "/api/bookings";
    public static final String BOOKING_BY_PNR = "/api/bookings/{pnr}";
    public static final String PAY_BOOKING = "/api/bookings/{id}/pay";
    public static final String CONFIRM_BOOKING = "/api/bookings/{id}/confirm";
    public static final String CANCEL_BOOKING = "/api/bookings/{id}/cancel";

    // Reset
    public static final String RESET = "/api/reset";
}