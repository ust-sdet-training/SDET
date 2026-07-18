package com.api.repository;

import com.api.model.Booking;

import java.sql.*;

public class BookingRepository {

    private final String jdbcUrl;
    private final String username;
    private final String password;

    public BookingRepository(
            String jdbcUrl,
            String username,
            String password) {

        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    public long save(Booking booking) {

        String sql =
                """
                INSERT INTO bookings
                (pnr,state,inventory_id,emp_id)
                VALUES(?,?,?,?)
                """;

        try (
                Connection connection =
                        DriverManager.getConnection(
                                jdbcUrl,
                                username,
                                password);

                PreparedStatement statement =
                        connection.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS)
        ) {

            statement.setString(1, booking.getPnr());
            statement.setString(2, booking.getState());
            statement.setString(3, booking.getInventoryId());
            statement.setString(4, booking.getEmpId());

            statement.executeUpdate();

            ResultSet rs =
                    statement.getGeneratedKeys();

            rs.next();

            return rs.getLong(1);

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    public int countBookings() {

        try (
                Connection connection =
                        DriverManager.getConnection(
                                jdbcUrl,
                                username,
                                password);

                PreparedStatement statement =
                        connection.prepareStatement(
                                "select count(*) from bookings");

                ResultSet rs = statement.executeQuery()
        ) {

            rs.next();

            return rs.getInt(1);

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    public int countByState(String state) {

        try (
                Connection connection =
                        DriverManager.getConnection(
                                jdbcUrl,
                                username,
                                password);

                PreparedStatement statement =
                        connection.prepareStatement(
                                "select count(*) from bookings where state=?")
        ) {

            statement.setString(1, state);

            ResultSet rs =
                    statement.executeQuery();

            rs.next();

            return rs.getInt(1);

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    public void resetTable() {

        try (
                Connection connection =
                        DriverManager.getConnection(
                                jdbcUrl,
                                username,
                                password);

                PreparedStatement statement =
                        connection.prepareStatement(
                                "truncate table bookings")
        ) {

            statement.execute();

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
}