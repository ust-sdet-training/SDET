package db;

import models.BookingRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingRepository {


    public BookingRecord getBookingByPnr(
            String pnr
    ) {

        String sql =
                """
                SELECT
                    id,
                    pnr,
                    emp_id,
                    inventory_id,
                    state,
                    refundable,
                    amount_paise
                FROM bookings
                WHERE pnr = ?
                """;


        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)

        ) {

            statement.setString(
                    1,
                    pnr
            );


            ResultSet resultSet =
                    statement.executeQuery();


            if (!resultSet.next()) {

                throw new RuntimeException(
                        "Booking not found : " + pnr
                );

            }


            BookingRecord booking =
                    new BookingRecord();


            booking.setId(
                    resultSet.getString("id")
            );

            booking.setPnr(
                    resultSet.getString("pnr")
            );

            booking.setEmpId(
                    resultSet.getString("emp_id")
            );

            booking.setInventoryId(
                    resultSet.getString("inventory_id")
            );

            booking.setState(
                    resultSet.getString("state")
            );

            booking.setRefundable(
                    resultSet.getBoolean("refundable")
            );

            booking.setAmountPaise(
                    resultSet.getLong("amount_paise")
            );


            return booking;

        }

        catch (SQLException exception) {

            throw new RuntimeException(
                    "Database query failed",
                    exception
            );

        }

    }

}