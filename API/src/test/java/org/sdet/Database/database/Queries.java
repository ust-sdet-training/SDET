package org.sdet.Database.database;

public final class Queries {

    private Queries() {
    }

    // Booking

    public static final String GET_BOOKING_BY_PNR = "SELECT * FROM bookings WHERE pnr=?";

    public static final String GET_BOOKING_BY_ID = "SELECT * FROM bookings WHERE booking_id=?";

    public static final String GET_BOOKING_STATE = "SELECT state FROM bookings WHERE booking_id=?";

    public static final String GET_BOOKING_COUNT = "SELECT COUNT(*) FROM bookings";

    // Flight

    public static final String GET_FLIGHT = "SELECT * FROM flights WHERE id=?";

    public static final String GET_FLIGHT_SEATS = "SELECT available_seats FROM flights WHERE id=?";


    // Payment

    public static final String GET_PAYMENT =
            "SELECT * FROM payments WHERE booking_id=?";

}