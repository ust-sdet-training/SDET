package org.sdet.constants;

public class Endpoints {

    // Authentication
    public static final String LOGIN = "/api/auth/login";
    public static final String ME = "/api/auth/me";
    public static final String ADMIN_PING = "/api/auth/admin-ping";

    // Flights
    public static final String FLIGHTS = "/api/flights";
    public static final String FLIGHT_SEATS = "/api/flights/{id}/seats";

    // Buses
    public static final String BUSES = "/api/buses";
    public static final String BUS_SEATS = "/api/buses/{id}/seats";

    // Bookings
    public static final String BOOKINGS = "/api/bookings";
    public static final String PAY = "/api/bookings/{id}/pay";
    public static final String CONFIRM = "/api/bookings/{id}/confirm";
    public static final String CANCEL = "/api/bookings/{id}/cancel";
    public static final String BOOKING_BY_PNR = "/api/bookings/{pnr}";

    // Reset
    public static final String RESET = "/api/reset";

}