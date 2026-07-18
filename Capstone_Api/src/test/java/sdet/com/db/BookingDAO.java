package sdet.com.db;

import java.sql.*;

public class BookingDAO {

    public static void saveBooking(
            String bookingId,
            String pnr,
            String passenger,
            String status
    ) throws Exception {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO bookings(booking_id,pnr,passenger_name,status) VALUES(?,?,?,?)"
        );

        ps.setString(1, bookingId);
        ps.setString(2, pnr);
        ps.setString(3, passenger);
        ps.setString(4, status);

        ps.executeUpdate();

        ps.close();
        con.close();
    }

    public static boolean bookingExists(String pnr) throws Exception {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM bookings WHERE pnr=?"
        );

        ps.setString(1, pnr);

        ResultSet rs = ps.executeQuery();

        boolean exists = rs.next();

        rs.close();
        ps.close();
        con.close();

        return exists;
    }

    public static String getBookingStatus(String pnr) throws Exception {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(
                "SELECT status FROM bookings WHERE pnr=?"
        );

        ps.setString(1, pnr);

        ResultSet rs = ps.executeQuery();

        rs.next();

        String status = rs.getString("status");

        rs.close();
        ps.close();
        con.close();

        return status;
    }
    public static String getStatus(String pnr) throws Exception {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps =
                con.prepareStatement(
                        "select status from bookings where pnr=?");

        ps.setString(1, pnr);

        ResultSet rs = ps.executeQuery();

        rs.next();

        return rs.getString("status");
    }

    public static String getPassengerName(String pnr) throws Exception {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps =
                con.prepareStatement(
                        "select passenger_name from bookings where pnr=?");

        ps.setString(1, pnr);

        ResultSet rs = ps.executeQuery();

        rs.next();

        return rs.getString("passenger_name");
    }

    public static int bookingCount() throws Exception {

        Connection con = DBConnection.getConnection();

        Statement st = con.createStatement();

        ResultSet rs = st.executeQuery(
                "SELECT COUNT(*) FROM bookings"
        );

        rs.next();

        int count = rs.getInt(1);

        rs.close();
        st.close();
        con.close();

        return count;
    }

    public static String getBookingId(String pnr) throws Exception {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps =
                con.prepareStatement(
                        "select booking_id from bookings where pnr=?");

        ps.setString(1, pnr);

        ResultSet rs = ps.executeQuery();

        rs.next();

        return rs.getString("booking_id");
    }

    public static int countBookings(String pnr) throws Exception {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps =
                con.prepareStatement(
                        "select count(*) from bookings where pnr=?");

        ps.setString(1, pnr);

        ResultSet rs = ps.executeQuery();

        rs.next();

        return rs.getInt(1);
    }


}