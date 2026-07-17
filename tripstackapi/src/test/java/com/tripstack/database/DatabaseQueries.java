package com.tripstack.database;

public final class DatabaseQueries {

    private DatabaseQueries() {
    }

    // ---------- USERS ----------

    public static final String GET_USER_BY_EMP_ID =
            "SELECT * FROM users WHERE emp_id = ?";

    public static final String GET_USER_BY_EMAIL =
            "SELECT * FROM users WHERE email = ?";


    // ---------- BOOKINGS ----------

    public static final String GET_BOOKING_BY_ID =
            "SELECT * FROM bookings WHERE id = ?";

    public static final String GET_BOOKING_BY_PNR =
            "SELECT * FROM bookings WHERE pnr = ?";

    public static final String GET_BOOKINGS_BY_EMPLOYEE =
            "SELECT * FROM bookings WHERE emp_id = ?";

    public static final String GET_BOOKING_COUNT =
            "SELECT COUNT(*) FROM bookings";

    public static final String DELETE_BOOKING =
            "DELETE FROM bookings WHERE id = ?";


    // ---------- PAYMENTS ----------

    public static final String GET_PAYMENT_BY_BOOKING =
            "SELECT * FROM payments WHERE booking_id = ?";

    public static final String GET_PAYMENT_COUNT =
            "SELECT COUNT(*) FROM payments";
}