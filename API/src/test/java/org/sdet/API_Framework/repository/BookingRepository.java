package org.sdet.API_Framework.repository;

import org.sdet.Database.database.DatabaseConnection;
import org.sdet.Database.database.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookingRepository {

    public ResultSet getBookingByPNR(String pnr) {

        try {

            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(
                            Queries.GET_BOOKING_BY_PNR);

            statement.setString(1, pnr);

            return statement.executeQuery();

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    public String getBookingState(String bookingId) {

        try {

            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(
                            Queries.GET_BOOKING_STATE);

            statement.setString(1, bookingId);

            ResultSet result = statement.executeQuery();

            if (result.next()) {

                return result.getString("state");
            }

        } catch (Exception e) {

            throw new RuntimeException(e);
        }

        return null;
    }

    public int getBookingCount() {

        try {

            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(
                            Queries.GET_BOOKING_COUNT);

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                return result.getInt(1);
            }

        } catch (Exception e) {

            throw new RuntimeException(e);
        }

        return 0;
    }
}