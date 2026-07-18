package com.routepulse.api.db;

import com.routepulse.api.config.TravelConfigManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseValidator {
    private final TravelConfigManager config;

    public DatabaseValidator(TravelConfigManager config) {
        this.config = config;
    }

    public boolean hasBookingForEmployee(String empId) throws SQLException {
        try {
            return executeCountQuery(empId) > 0;
        } catch (SQLException ex) {
            return false;
        }
    }

    public String latestBookingStateForEmployee(String empId) throws SQLException {
        try {
            return executeLatestStateQuery(empId);
        } catch (SQLException ex) {
            return null;
        }
    }

    private int executeCountQuery(String empId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM bookings WHERE emp_id = ?";
        try (Connection connection = DriverManager.getConnection(config.getDbUrl(), config.getDbUser(), config.getDbPassword());
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, empId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    private String executeLatestStateQuery(String empId) throws SQLException {
        String sql = "SELECT state FROM bookings WHERE emp_id = ? ORDER BY id DESC LIMIT 1";
        try (Connection connection = DriverManager.getConnection(config.getDbUrl(), config.getDbUser(), config.getDbPassword());
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, empId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("state");
                }
                return null;
            }
        }
    }
}
