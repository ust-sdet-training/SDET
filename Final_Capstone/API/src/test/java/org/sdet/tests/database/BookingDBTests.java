package org.sdet.tests.database;

import org.junit.jupiter.api.Test;
import org.sdet.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

public class BookingDBTests {

    @Test
    public void verifyBookingExistsInDatabase() throws Exception {

        String pnr = "TS-1027-0001";   // Replace with PNR returned from API

        Connection connection = DatabaseConnection.getConnection();

        String query = "SELECT * FROM bookings WHERE pnr = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, pnr);

        ResultSet result = statement.executeQuery();

        assertTrue(result.next(), "Booking not found in database");

        assertEquals(pnr, result.getString("pnr"));

        connection.close();
    }
}