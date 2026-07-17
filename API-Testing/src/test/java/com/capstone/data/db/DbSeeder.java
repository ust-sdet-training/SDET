package com.capstone.data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public final class DbSeeder {

    public static final String BOOKING_ID = "seed-booking-0001";
    public static final String PNR = "TS-LOCAL-0001";
    public static final String EMP_ID = "1006";
    public static final String INVENTORY_ID = "BUS-DELBOM-03";
    public static final String SEAT_ID = "S1";

    private static final String CREATE_BOOKINGS_TABLE = """
            CREATE TABLE IF NOT EXISTS bookings (
                id VARCHAR(36) NOT NULL PRIMARY KEY,
                pnr VARCHAR(32) NOT NULL UNIQUE,
                emp_id VARCHAR(20) NOT NULL,
                journey_type VARCHAR(20) NOT NULL,
                inventory_id VARCHAR(50) NOT NULL,
                state VARCHAR(30) NOT NULL,
                seat_ids VARCHAR(255) NOT NULL,
                amount_paise BIGINT NOT NULL,
                refundable BOOLEAN NOT NULL,
                hold_expires_at TIMESTAMP NOT NULL,
                created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
            )
            """;

    private static final String UPSERT_BOOKING = """
            INSERT INTO bookings (
                id, pnr, emp_id, journey_type, inventory_id, state,
                seat_ids, amount_paise, refundable, hold_expires_at
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
                emp_id = VALUES(emp_id),
                journey_type = VALUES(journey_type),
                inventory_id = VALUES(inventory_id),
                state = VALUES(state),
                seat_ids = VALUES(seat_ids),
                amount_paise = VALUES(amount_paise),
                refundable = VALUES(refundable),
                hold_expires_at = VALUES(hold_expires_at)
            """;

    private DbSeeder() {
    }

    public static void seedConfirmedBusBooking() {
        try (Connection connection = DbConfig.connection();
             PreparedStatement createTable = connection.prepareStatement(CREATE_BOOKINGS_TABLE)) {

            createTable.execute();

            try (PreparedStatement upsert = connection.prepareStatement(UPSERT_BOOKING)) {
                upsert.setString(1, BOOKING_ID);
                upsert.setString(2, PNR);
                upsert.setString(3, EMP_ID);
                upsert.setString(4, "bus");
                upsert.setString(5, INVENTORY_ID);
                upsert.setString(6, "CONFIRMED");
                upsert.setString(7, "[\"" + SEAT_ID + "\"]");
                upsert.setLong(8, 129885L);
                upsert.setBoolean(9, true);
                upsert.setTimestamp(10, Timestamp.valueOf("2026-08-04 21:39:00"));
                upsert.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to seed the local bookings table", exception);
        }
    }
}
