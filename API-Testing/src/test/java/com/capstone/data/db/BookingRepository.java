package com.capstone.data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class BookingRepository extends DbSupport {

    public Optional<BookingRecord> findById(String bookingId) throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(SqlQueries.BOOKING_BY_ID)) {

            statement.setString(1, bookingId);
            return findBooking(statement);
        }
    }

    public Optional<BookingRecord> findByPnr(String pnr) throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(SqlQueries.BOOKING_BY_PNR)) {

            statement.setString(1, pnr);
            return findBooking(statement);
        }
    }

    private Optional<BookingRecord> findBooking(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(new BookingRecord(
                    resultSet.getString("id"),
                    resultSet.getString("pnr"),
                    resultSet.getString("emp_id"),
                    resultSet.getString("journey_type"),
                    resultSet.getString("inventory_id"),
                    resultSet.getString("state"),
                    resultSet.getString("seat_ids"),
                    resultSet.getObject("amount_paise", Long.class),
                    resultSet.getBoolean("refundable")
            ));
        }
    }
}
