package com.apitesting.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DbSetup {

    private static final String URL =
            "jdbc:mysql://localhost:3306/";

    private static final String USER =
            "root";

    private static final String PASSWORD =
            "Formysql@123";

    public static void createDatabase() {

        try (
                Connection con =
                        DriverManager.getConnection(URL, USER, PASSWORD);

                Statement stmt =
                        con.createStatement()
        ) {

            stmt.executeUpdate(
                    "CREATE DATABASE IF NOT EXISTS tripstack_db"
            );

            System.out.println("Database created");

        } catch (Exception e) {

            throw new RuntimeException(e);

        }
    }

    public static void createBookingsTable() {

        try (
                Connection con =
                        DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/tripstack_db",
                                USER,
                                PASSWORD
                        );

                Statement stmt =
                        con.createStatement()
        ) {

            stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS bookings (
                    
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        
                        booking_id VARCHAR(100),
                        
                        pnr VARCHAR(100),
                        
                        emp_id VARCHAR(20),
                        
                        journey_type VARCHAR(50),
                        
                        source_city VARCHAR(20),
                        
                        destination_city VARCHAR(20),
                        
                        booking_status VARCHAR(50),
                        
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    )
                    """);

            System.out.println("Bookings table created");

        } catch (Exception e) {

            throw new RuntimeException(e);

        }
    }

    public static void insertFlightAudit(
            String from,
            String to,
            String empId,
            String responseBody
    ) {

        try (
                Connection con =
                        DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/tripstack_db",
                                USER,
                                PASSWORD
                        );

                PreparedStatement ps =
                        con.prepareStatement("""
                            INSERT INTO bookings
                            (
                                emp_id,
                                source_city,
                                destination_city,
                                booking_status
                            )
                            VALUES
                            (?,?,?,?)
                            """)
        ) {

            ps.setString(1, empId);
            ps.setString(2, from);
            ps.setString(3, to);
            ps.setString(4, "API_SUCCESS");

            ps.executeUpdate();

        } catch (Exception e) {

            throw new RuntimeException(e);

        }
    }

}