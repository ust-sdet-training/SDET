package com.tripstack.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DatabaseValidator {

    private DatabaseValidator() {
    }

    public static boolean userExists(String empId) throws SQLException {

        Connection connection = DBUtils.getConnection();

        ResultSet rs = DBUtils.executeQuery(
                connection,
                DatabaseQueries.GET_USER_BY_EMP_ID,
                empId);

        boolean exists = rs.next();

        rs.close();
        DBUtils.close(connection);

        return exists;
    }

    public static boolean bookingExists(String bookingId) throws SQLException {

        Connection connection = DBUtils.getConnection();

        ResultSet rs = DBUtils.executeQuery(
                connection,
                DatabaseQueries.GET_BOOKING_BY_ID,
                bookingId);

        boolean exists = rs.next();

        rs.close();
        DBUtils.close(connection);

        return exists;
    }

    public static String getBookingStatus(String bookingId)
            throws SQLException {

        Connection connection = DBUtils.getConnection();

        ResultSet rs = DBUtils.executeQuery(
                connection,
                DatabaseQueries.GET_BOOKING_BY_ID,
                bookingId);

        String status = null;

        if (rs.next()) {
            status = rs.getString("status");
        }

        rs.close();
        DBUtils.close(connection);

        return status;
    }

    public static String getBookingPNR(String bookingId)
            throws SQLException {

        Connection connection = DBUtils.getConnection();

        ResultSet rs = DBUtils.executeQuery(
                connection,
                DatabaseQueries.GET_BOOKING_BY_ID,
                bookingId);

        String pnr = null;

        if (rs.next()) {
            pnr = rs.getString("pnr");
        }

        rs.close();
        DBUtils.close(connection);

        return pnr;
    }

    public static String getEmployeeId(String bookingId)
            throws SQLException {

        Connection connection = DBUtils.getConnection();

        ResultSet rs = DBUtils.executeQuery(
                connection,
                DatabaseQueries.GET_BOOKING_BY_ID,
                bookingId);

        String empId = null;

        if (rs.next()) {
            empId = rs.getString("emp_id");
        }

        rs.close();
        DBUtils.close(connection);

        return empId;
    }

    public static boolean paymentExists(String bookingId)
            throws SQLException {

        Connection connection = DBUtils.getConnection();

        ResultSet rs = DBUtils.executeQuery(
                connection,
                DatabaseQueries.GET_PAYMENT_BY_BOOKING,
                bookingId);

        boolean exists = rs.next();

        rs.close();
        DBUtils.close(connection);

        return exists;
    }
}