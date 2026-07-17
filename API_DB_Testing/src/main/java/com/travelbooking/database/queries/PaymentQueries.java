package com.travelbooking.database.queries;

import com.travelbooking.database.DatabaseHelper;
import com.travelbooking.database.models.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentQueries {

    public Payment getPaymentByBookingId(int bookingId) {

        String query = "SELECT * FROM payments WHERE booking_id = ?";

        ResultSet rs = DatabaseHelper.executeQuery(query, bookingId);

        try {

            if (rs.next()) {

                Payment payment = new Payment();

                payment.setPaymentId(rs.getInt("payment_id"));
                payment.setBookingId(rs.getInt("booking_id"));
                payment.setPaymentMethod(rs.getString("payment_method"));
                payment.setPaymentStatus(rs.getString("payment_status"));
                payment.setAmount(rs.getBigDecimal("amount"));

                return payment;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}