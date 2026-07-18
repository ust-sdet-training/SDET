package com.apitesting.repository;

import java.sql.*;

public class BookingRepository {

    private final String jdbcUrl;
    private final String username;
    private final String password;

    public BookingRepository(String jdbcUrl,
                             String username,
                             String password) {

        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection(
                jdbcUrl,
                username,
                password
        );
    }

    public record Booking(
            String pnr,
            String inventoryId,
            String journeyType,
            String status,
            String empId,
            boolean refundable,
            String seatId
    ) {
    }

    public void save(Booking booking) {

        String sql = """
            INSERT INTO bookings
            (pnr, inventory_id, journey_type,
             status, emp_id, refundable, seat_id)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (
                Connection con = connection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, booking.pnr());
            ps.setString(2, booking.inventoryId());
            ps.setString(3, booking.journeyType());
            ps.setString(4, booking.status());
            ps.setString(5, booking.empId());
            ps.setBoolean(6, booking.refundable());
            ps.setString(7, booking.seatId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Booking findByPnr(String pnr) {

        String sql = """
            SELECT *
            FROM bookings
            WHERE pnr = ?
            """;

        try (
                Connection con = connection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, pnr);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            return new Booking(
                    rs.getString("pnr"),
                    rs.getString("inventory_id"),
                    rs.getString("journey_type"),
                    rs.getString("status"),
                    rs.getString("emp_id"),
                    rs.getBoolean("refundable"),
                    rs.getString("seat_id")
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void reset() {

        try (
                Connection con = connection();
                Statement st = con.createStatement()
        ) {

            st.execute("TRUNCATE TABLE bookings");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}