package com.tripstack.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseManager {

    private static final String DB_URL  = System.getenv("TRIPSTACK_DB_URL");
    private static final String DB_USER = System.getenv("TRIPSTACK_DB_USER");
    private static final String DB_PASS = System.getenv("TRIPSTACK_DB_PASS");

    public static Connection getConnection() throws Exception {
        if (DB_URL == null || DB_URL.isBlank()) {
            throw new IllegalStateException(
                    "TRIPSTACK_DB_URL is not set. Ask your director for the JDBC connection string.");
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    public static ResultSet findBookingByPnr(Connection conn, String pnr) throws Exception {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM bookings WHERE pnr = ?");
        ps.setString(1, pnr);
        return ps.executeQuery();
    }
}