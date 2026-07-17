package com.travelbooking.database.queries;

import com.travelbooking.database.DatabaseHelper;
import com.travelbooking.database.models.Passenger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PassengerQueries {

    public Passenger getPassengerByBookingId(int bookingId) {

        String query = "SELECT * FROM passengers WHERE booking_id = ?";

        ResultSet rs = DatabaseHelper.executeQuery(query, bookingId);

        try {

            if (rs.next()) {

                Passenger passenger = new Passenger();

                passenger.setPassengerId(rs.getInt("passenger_id"));
                passenger.setBookingId(rs.getInt("booking_id"));
                passenger.setFirstName(rs.getString("first_name"));
                passenger.setLastName(rs.getString("last_name"));
                passenger.setAge(rs.getInt("age"));
                passenger.setGender(rs.getString("gender"));

                return passenger;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}