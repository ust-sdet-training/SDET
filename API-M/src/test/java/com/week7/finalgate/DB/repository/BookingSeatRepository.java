package com.week7.finalgate.DB.repository;

import com.week7.finalgate.DB.support.JdbcUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BookingSeatRepository {

    public void saveSeats(
            String bookingUuid,
            List<String> seats
    ) {
        if (seats == null || seats.isEmpty()) {
            return;
        }

        String sql =
                """
                INSERT INTO booking_seats
                (
                    booking_uuid,
                    seat_id
                )
                VALUES (?,?)
                """;

        try (

                Connection connection =
                        JdbcUtil.getConnection();

                PreparedStatement ps =
                        connection.prepareStatement(sql)

        ) {

            for (String seat : seats) {

                ps.setString(1, bookingUuid);

                ps.setString(2, seat);

                ps.addBatch();

            }

            ps.executeBatch();

        }

        catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }
    public List<String> findSeatsByBookingUuid(
            String bookingUuid
    ) {

        List<String> seats =
                new ArrayList<>();

        String sql =
                """
                SELECT seat_id
                FROM booking_seats
                WHERE booking_uuid=?
                """;

        try (

                Connection connection =
                        JdbcUtil.getConnection();

                PreparedStatement ps =
                        connection.prepareStatement(sql)

        ) {

            ps.setString(1, bookingUuid);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                seats.add(
                        rs.getString("seat_id")
                );

            }

        }

        catch (SQLException e) {

            throw new RuntimeException(e);

        }

        return seats;

    }
    public void deleteSeats(String bookingUuid) {

        String sql = """
            DELETE FROM booking_seats
            WHERE booking_uuid = ?
            """;

        try (
                Connection connection = JdbcUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setString(1, bookingUuid);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
