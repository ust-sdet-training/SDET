package com.travelbooking.database.queries;

import com.travelbooking.database.DatabaseHelper;
import com.travelbooking.database.models.Booking;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingQueries {

    public Booking getBookingById(int bookingId) {

        String query = "SELECT * FROM bookings WHERE booking_id = ?";

        ResultSet rs = DatabaseHelper.executeQuery(query, bookingId);

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
}