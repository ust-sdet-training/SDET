package com.ust.tripStack.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ust.tripStack.config.DatabaseConfig;
import com.ust.tripStack.model.BookingRow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class BookingRepository {

    private final DatabaseConfig config;
    private final ObjectMapper mapper = new ObjectMapper();

    public BookingRepository(DatabaseConfig config) {
        this.config = config;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(config.jdbcUrl(), config.username(), config.password());
    }

    public void insert(BookingRow row) {
        String sql = """
                INSERT INTO bookings
                    (id, pnr, emp_id, journey_type, inventory_id, state, seat_ids, amount_paise, refundable, hold_expires_at)
                VALUES (?, ?, ?, ?, ?, ?, CAST(? AS JSON), ?, ?, ?)
                """;
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, row.id());
            ps.setString(2, row.pnr());
            ps.setString(3, row.empId());
            ps.setString(4, row.journeyType());
            ps.setString(5, row.inventoryId());
            ps.setString(6, row.state());
            ps.setString(7, toJson(row.seatIds()));
            ps.setLong(8, row.amountPaise());
            ps.setBoolean(9, row.refundable());
            ps.setTimestamp(10, row.holdExpiresAt() != null ? Timestamp.from(row.holdExpiresAt()) : null);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert booking row: " + row.id(), e);
        }
    }

    public BookingRow findByPnr(String pnr) {
        return findOneBy("pnr", pnr);
    }

    public BookingRow findById(String id) {
        return findOneBy("id", id);
    }

    private BookingRow findOneBy(String column, String value) {
        String sql = "SELECT * FROM bookings WHERE " + column + " = ?";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, value);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to look up booking by " + column + "=" + value, e);
        }
    }

    public int deleteAll() {
        try (Connection conn = connect(); Statement st = conn.createStatement()) {
            return st.executeUpdate("DELETE FROM bookings");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clear bookings table", e);
        }
    }

    private BookingRow mapRow(ResultSet rs) throws SQLException {
        Timestamp holdExpiresAt = rs.getTimestamp("hold_expires_at");
        return new BookingRow(
                rs.getString("id"),
                rs.getString("pnr"),
                rs.getString("emp_id"),
                rs.getString("journey_type"),
                rs.getString("inventory_id"),
                rs.getString("state"),
                fromJson(rs.getString("seat_ids")),
                rs.getLong("amount_paise"),
                rs.getBoolean("refundable"),
                holdExpiresAt != null ? holdExpiresAt.toInstant() : null
        );
    }

    private String toJson(List<String> seatIds) {
        try {
            return mapper.writeValueAsString(seatIds);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize seatIds", e);
        }
    }

    private List<String> fromJson(String json) {
        try {
            return Arrays.asList(mapper.readValue(json, String[].class));
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize seat_ids: " + json, e);
        }
    }
}