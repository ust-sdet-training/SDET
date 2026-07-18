package com.travelbooking.database.queries;

import com.travelbooking.database.DatabaseHelper;
import com.travelbooking.database.models.Booking;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingQueries {

    public Booking getBookingByPnr(String pnr) {

        String query = "SELECT * FROM bookings WHERE pnr = ?";

        ResultSet rs = DatabaseHelper.executeQuery(query, pnr);

        try {

            if (rs.next()) {

                Booking booking = new Booking();

                booking.setBookingId(rs.getInt("booking_id"));
                booking.setPnr(rs.getString("pnr"));
                booking.setSourceCity(rs.getString("source_city"));
                booking.setDestinationCity(rs.getString("destination_city"));
                booking.setJourneyDate(rs.getDate("journey_date").toLocalDate());
                booking.setTotalAmount(rs.getBigDecimal("total_amount"));
                booking.setBookingStatus(rs.getString("booking_status"));

                return booking;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void saveBooking(String pnr,
                            String sourceCity,
                            String destinationCity,
                            String bookingStatus,
                            double totalAmount) {

        String query = """
            INSERT INTO bookings
            (pnr, source_city, destination_city, journey_date, total_amount, booking_status)
            VALUES (?, ?, ?, CURDATE(), ?, ?)
            """;

        DatabaseHelper.executeUpdate(
                query,
                pnr,
                sourceCity,
                destinationCity,
                totalAmount,
                bookingStatus
        );
    }
}