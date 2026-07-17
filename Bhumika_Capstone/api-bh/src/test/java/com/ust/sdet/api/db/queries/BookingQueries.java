package com.ust.sdet.api.db.queries;

import com.ust.sdet.api.db.config.DatabaseConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BookingQueries {
    private static Connection connection;
    public static void init() {
        connection = DatabaseConnection.getConnection();
    }
    public static Map<String, Object> getBookingById(String bookingId) {
        if (connection == null) {
            System.out.println("Database not available - skipping DB verification");
            return null;
        }

        try {
            String query = "SELECT id, pnr, state, emp_id, inventory_id, seat_ids, amount_paise, refundable, hold_expires_at " +
                           "FROM bookings WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, bookingId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Map<String, Object> booking = new HashMap<>();
                booking.put("id", rs.getString("id"));
                booking.put("pnr", rs.getString("pnr"));
                booking.put("state", rs.getString("state"));
                booking.put("empId", rs.getInt("emp_id"));
                booking.put("inventoryId", rs.getString("inventory_id"));
                booking.put("seatIds", rs.getString("seat_ids"));
                booking.put("amountPaise", rs.getLong("amount_paise"));
                booking.put("refundable", rs.getBoolean("refundable"));
                booking.put("holdExpiresAt", rs.getTimestamp("hold_expires_at"));
                return booking;
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error fetching booking: " + e.getMessage());
            return null;
        }
    }

    public static Map<String, Object> getBookingByPnr(String pnr) {
        if (connection == null) {
            System.out.println("Database not available - skipping DB verification");
            return null;
        }

        try {
            String query = "SELECT id, pnr, state, emp_id, inventory_id, seat_ids, amount_paise, refundable " +
                           "FROM bookings WHERE pnr = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, pnr);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Map<String, Object> booking = new HashMap<>();
                booking.put("id", rs.getString("id"));
                booking.put("pnr", rs.getString("pnr"));
                booking.put("state", rs.getString("state"));
                booking.put("empId", rs.getInt("emp_id"));
                booking.put("inventoryId", rs.getString("inventory_id"));
                booking.put("seatIds", rs.getString("seat_ids"));
                booking.put("amountPaise", rs.getLong("amount_paise"));
                booking.put("refundable", rs.getBoolean("refundable"));
                return booking;
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error fetching booking by PNR: " + e.getMessage());
            return null;
        }
    }

    public static int getEmployeeBookingCount(int empId) {
        if (connection == null) {
            System.out.println("Database not available - skipping DB verification");
            return -1;
        }

        try {
            String query = "SELECT COUNT(*) as count FROM bookings WHERE emp_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, empId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("count");
            }
            return 0;
        } catch (SQLException e) {
            System.err.println("Error counting employee bookings: " + e.getMessage());
            return -1;
        }
    }

    public static void close() {
        DatabaseConnection.closeConnection();
    }
}
