package com.capstone.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.capstone.config.EnvConfig;

public class DbClient {
  public Connection connect() throws SQLException {
    String jdbcUrl = EnvConfig.require("DB_URL");
    return DriverManager.getConnection(jdbcUrl, EnvConfig.require("DB_USER"), EnvConfig.require("DB_PASSWORD"));
  }

  public int countBookings() throws SQLException {
    try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM bookings")) {
      ResultSet rs = stmt.executeQuery();
      rs.next();
      return rs.getInt(1);
    }
  }
}
