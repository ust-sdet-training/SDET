package com.apitesting.data.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookingRepository {

    private static final Logger log =
            LoggerFactory.getLogger(BookingRepository.class);

    private final String jdbcUrl;
    private final String username;
    private final String password;

    public BookingRepository(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    public String save(BookingData booking) {
        String bookingSql = """
                INSERT INTO bookings(id, customer_name, status, pnr)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = connection()) {
            try (PreparedStatement bookingStatement = connection.prepareStatement(bookingSql)) {
                bookingStatement.setString(1, booking.id());
                bookingStatement.setString(2, booking.customerName());
                bookingStatement.setString(3, booking.status());
                bookingStatement.setString(4, booking.pnr());

                bookingStatement.executeUpdate();
            }

            return booking.id();

        } catch (SQLException e) {
            throw new IllegalStateException("Could not save booking test data", e);
        }
    }

    public int count() {
        return queryForInt("SELECT COUNT(*) FROM bookings");
    }

    public int countByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM bookings WHERE status = ?";

        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Could not count bookings by status", e);
        }
    }

    public void resetMutableTables() {
        try (Connection connection = connection();
             Statement statement = connection.createStatement()) {

            statement.execute("SET FOREIGN_KEY_CHECKS = 0");
            statement.execute("TRUNCATE TABLE bookings");
            statement.execute("SET FOREIGN_KEY_CHECKS = 1");

        } catch (SQLException e) {
            throw new IllegalStateException("Could not reset booking test data", e);
        }
    }

    private int queryForInt(String sql) {
        try (Connection connection = connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            resultSet.next();
            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new IllegalStateException("Could not run count query", e);
        }
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    public record BookingData(
            String id,
            String customerName,
            String status,
            String pnr
    ) {
    }
}