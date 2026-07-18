package org.sdet.API_Framework.constants;

public final class Endpoints {

    private Endpoints() {}


    public static final String LOGIN = "/api/auth/login";

    public static final String ME = "/api/auth/me";

    public static final String ADMIN_PING = "/api/auth/admin-ping";


    public static final String FLIGHTS = "/api/flights";

    public static final String FLIGHT_SEATS = "/api/flights/{id}/seats";



    public static final String BOOKINGS = "/api/bookings";

    public static final String BOOKING_BY_PNR = "/api/bookings/{pnr}";

    public static final String PAY_BOOKING = "/api/bookings/{bookingId}/pay";

    public static final String CONFIRM_BOOKING = "/api/bookings/{bookingId}/confirm";

    public static final String CANCEL_BOOKING = "/api/bookings/{bookingId}/cancel";


    public static final String MY_BOOKINGS = "/api/bookings";


    public static final String RESET = "/api/reset";

}