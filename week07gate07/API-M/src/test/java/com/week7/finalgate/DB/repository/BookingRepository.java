package com.week7.finalgate.DB.repository;

import com.week7.finalgate.DB.model.BookingRecord;
import com.week7.finalgate.DB.support.JdbcUtil;

import java.sql.*;
import java.util.Optional;

public class BookingRepository {

    public void save(BookingRecord booking) {

        String sql = """
    INSERT INTO bookings (
        booking_uuid,
        pnr,
        emp_id,
        journey_type,
        inventory_id,
        booking_state,
        amount_paise,
        refundable,
        hold_expires_at
    )
    VALUES (?,?,?,?,?,?,?,?,?)
    ON DUPLICATE KEY UPDATE
        pnr = VALUES(pnr),
        emp_id = VALUES(emp_id),
        journey_type = VALUES(journey_type),
        inventory_id = VALUES(inventory_id),
        booking_state = VALUES(booking_state),
        amount_paise = VALUES(amount_paise),
        refundable = VALUES(refundable),
        hold_expires_at = VALUES(hold_expires_at)
    """;

        try (
                Connection connection = JdbcUtil.getConnection();
                PreparedStatement ps =
                        connection.prepareStatement(sql)
        ) {

            ps.setString(1, booking.getBookingUuid());
            ps.setString(2, booking.getPnr());
            ps.setString(3, booking.getEmpId());
            ps.setString(4, booking.getJourneyType());
            ps.setString(5, booking.getInventoryId());
            ps.setString(6, booking.getBookingState());
            ps.setInt(7, booking.getAmountPaise());
            ps.setBoolean(8, booking.isRefundable());
            ps.setTimestamp(9, booking.getHoldExpiresAt());

            ps.executeUpdate();

        }

        catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    public Optional<BookingRecord> findByPNR(
            String pnr
    ) {

        String sql =
                """
                SELECT *
                FROM bookings
                WHERE pnr = ?
                """;

        try (

                Connection connection =
                        JdbcUtil.getConnection();

                PreparedStatement ps =
                        connection.prepareStatement(sql)

        ) {

            ps.setString(1, pnr);

            ResultSet rs =
                    ps.executeQuery();

            if (!rs.next()) {

                return Optional.empty();

            }

            BookingRecord booking =
                    map(rs);

            return Optional.of(booking);

        }

        catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    public Optional<BookingRecord> findByBookingId(
            String bookingId
    ) {

        String sql =
                """
                SELECT *
                FROM bookings
                WHERE booking_uuid=?
                """;

        try (

                Connection connection =
                        JdbcUtil.getConnection();

                PreparedStatement ps =
                        connection.prepareStatement(sql)

        ) {

            ps.setString(1, bookingId);

            ResultSet rs =
                    ps.executeQuery();

            if (!rs.next()) {

                return Optional.empty();

            }

            return Optional.of(
                    map(rs)
            );

        }

        catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    private BookingRecord map(
            ResultSet rs
    ) throws SQLException {

        BookingRecord booking =
                new BookingRecord();

        booking.setBookingUuid(
                rs.getString("booking_uuid")
        );

        booking.setPnr(
                rs.getString("pnr")
        );

        booking.setEmpId(
                rs.getString("emp_id")
        );

        booking.setJourneyType(
                rs.getString("journey_type")
        );

        booking.setInventoryId(
                rs.getString("inventory_id")
        );

        booking.setBookingState(
                rs.getString("booking_state")
        );

        booking.setAmountPaise(
                rs.getInt("amount_paise")
        );

        booking.setRefundable(
                rs.getBoolean("refundable")
        );

        booking.setHoldExpiresAt(
                rs.getTimestamp("hold_expires_at")
        );

        booking.setCreatedAt(
                rs.getTimestamp("created_at")
        );

        return booking;

    }

}