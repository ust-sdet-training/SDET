package com.tripstack.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tripstack.model.BookingResponse;

public class BookingRepository {

    private static final String FIND_BOOKING_BY_PNR = """
            SELECT
                booking_id,
                pnr,
                emp_id,
                journey_type,
                inventory_id,
                state,
                amount_paise,
                refundable,
                hold_expires_at
            FROM bookings
            WHERE pnr = ?
            """;

    private static final String FIND_BOOKING_BY_ID = """
            SELECT
                booking_id,
                pnr,
                emp_id,
                journey_type,
                inventory_id,
                state,
                amount_paise,
                refundable,
                hold_expires_at
            FROM bookings
            WHERE booking_id = ?
            """;

    private static final String FIND_BOOKINGS_BY_EMPLOYEE = """
            SELECT pnr
            FROM bookings
            WHERE emp_id = ?
            """;

    public BookingResponse findBookingByPnr(String pnr) {
        return findBooking(FIND_BOOKING_BY_PNR, pnr);
    }

    public BookingResponse findBookingById(String bookingId) {
        return findBooking(FIND_BOOKING_BY_ID, bookingId);
    }

    private BookingResponse findBooking(String sql, String value) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, value);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }
                BookingResponse booking = new BookingResponse();
                booking.setId(resultSet.getString("booking_id"));
                booking.setPnr(resultSet.getString("pnr"));
                booking.setEmpId(resultSet.getString("emp_id"));
                booking.setJourneyType(resultSet.getString("journey_type"));
                booking.setInventoryId(resultSet.getString("inventory_id"));
                booking.setState(resultSet.getString("state"));
                booking.setAmountPaise(resultSet.getLong("amount_paise"));
                booking.setRefundable(resultSet.getBoolean("refundable"));
                if (resultSet.getTimestamp("hold_expires_at") != null) {
                    booking.setHoldExpiresAt(resultSet.getTimestamp("hold_expires_at").toInstant());
                }
                return booking;
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to fetch booking for value: " + value, exception);
        }
    }

    public boolean bookingExists(String pnr) {
        return findBookingByPnr(pnr) != null;
    }

    public List<String> findBookingsForEmployee(String employeeId) {
        List<String> pnrs = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BOOKINGS_BY_EMPLOYEE)) {
            statement.setString(1, employeeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    pnrs.add(resultSet.getString("pnr"));
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to fetch bookings for employee: " + employeeId, exception);
        }
        return pnrs;
    }
}
