package com.capstone.data.db;

public class SqlQueries {
    private SqlQueries() {}

    public static final String BOOKING_BY_ID = """
            SELECT id, pnr, emp_id, journey_type, inventory_id, state, seat_ids, amount_paise, refundable
            FROM bookings
            WHERE id = ?
            """;

    public static final String BOOKING_BY_PNR = """
            SELECT id, pnr, emp_id, journey_type, inventory_id, state, seat_ids, amount_paise, refundable
            FROM bookings
            WHERE pnr = ?
            """;
}
