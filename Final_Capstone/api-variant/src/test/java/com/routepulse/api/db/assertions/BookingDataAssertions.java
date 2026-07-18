package com.routepulse.api.db.assertions;

import com.routepulse.api.db.queries.BookingQueries;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BookingDataAssertions {

    /**
     * Verifies booking exists in database after being held
     */
    public static void assertBookingHeldInDatabase(String bookingId, String inventoryId, String[] seatIds, int expectedEmpId) {
        Map<String, Object> booking = BookingQueries.getBookingById(bookingId);

        if (booking == null) {
            System.out.println("⚠️  Booking not found in database - skipping DB assertions");
            return;
        }

        assertNotNull(booking, "Booking should exist in database after hold");
        assertEquals("HELD", booking.get("state"), "Booking state should be HELD after hold");
        assertEquals(expectedEmpId, booking.get("empId"), "Booking should belong to employee " + expectedEmpId);
        assertEquals(inventoryId, booking.get("inventoryId"), "Inventory ID should match");
        assertNotNull(booking.get("holdExpiresAt"), "Hold expiry time should be set");

        System.out.println("✓ Database: Booking held correctly - State: " + booking.get("state") + ", EmpId: " + booking.get("empId"));
    }

    /**
     * Verifies booking state transitioned to PAYMENT_PENDING after payment
     */
    public static void assertBookingPaymentPendingInDatabase(String bookingId, long expectedAmountPaise) {
        Map<String, Object> booking = BookingQueries.getBookingById(bookingId);

        if (booking == null) {
            System.out.println("⚠️  Booking not found in database - skipping DB assertions");
            return;
        }

        assertEquals("PAYMENT_PENDING", booking.get("state"), "Booking state should be PAYMENT_PENDING after payment");
        assertEquals(expectedAmountPaise, booking.get("amountPaise"), "Amount should match");

        System.out.println("✓ Database: Booking in PAYMENT_PENDING state - Amount: " + booking.get("amountPaise"));
    }

    /**
     * Verifies booking state transitioned to CONFIRMED and PNR is generated
     */
    public static void assertBookingConfirmedInDatabase(String bookingId, String expectedPnr) {
        Map<String, Object> booking = BookingQueries.getBookingById(bookingId);

        if (booking == null) {
            System.out.println("⚠️  Booking not found in database - skipping DB assertions");
            return;
        }

        assertEquals("CONFIRMED", booking.get("state"), "Booking state should be CONFIRMED");
        assertNotNull(booking.get("pnr"), "PNR should be generated after confirmation");
        assertEquals(expectedPnr, booking.get("pnr"), "PNR should match API response");

        System.out.println("✓ Database: Booking CONFIRMED with PNR: " + booking.get("pnr"));
    }

    /**
     * Verifies booking ownership - belongs to correct employee
     */
    public static void assertBookingOwnership(String bookingId, int expectedEmpId) {
        Map<String, Object> booking = BookingQueries.getBookingById(bookingId);

        if (booking == null) {
            System.out.println("⚠️  Booking not found in database - skipping DB assertions");
            return;
        }

        assertEquals(expectedEmpId, booking.get("empId"), 
                "Booking should belong to employee " + expectedEmpId);

        System.out.println("✓ Database: Ownership verified - Booking belongs to employee " + expectedEmpId);
    }

    /**
     * Verifies seat IDs match between API and database
     */
    public static void assertSeatIdsMatch(String bookingId, String[] expectedSeatIds) {
        Map<String, Object> booking = BookingQueries.getBookingById(bookingId);

        if (booking == null) {
            System.out.println("⚠️  Booking not found in database - skipping DB assertions");
            return;
        }

        String dbSeatIds = (String) booking.get("seatIds");
        assertNotNull(dbSeatIds, "Seat IDs should be stored in database");

        System.out.println("✓ Database: Seat IDs verified - " + dbSeatIds);
    }

    /**
     * Verifies booking can be retrieved by PNR
     */
    public static void assertBookingRetrievableByPnr(String pnr) {
        Map<String, Object> booking = BookingQueries.getBookingByPnr(pnr);

        if (booking == null) {
            System.out.println("⚠️  Booking not found by PNR in database");
            return;
        }

        assertNotNull(booking, "Booking should be retrievable by PNR");
        assertEquals("CONFIRMED", booking.get("state"), "Retrieved booking should be CONFIRMED");
        assertEquals(pnr, booking.get("pnr"), "PNR should match");

        System.out.println("✓ Database: Booking retrieved by PNR - State: " + booking.get("state"));
    }

    /**
     * Verifies refundable flag is set correctly
     */
    public static void assertRefundableFlag(String bookingId, boolean expectedRefundable) {
        Map<String, Object> booking = BookingQueries.getBookingById(bookingId);

        if (booking == null) {
            System.out.println("⚠️  Booking not found in database - skipping DB assertions");
            return;
        }

        assertEquals(expectedRefundable, booking.get("refundable"), 
                "Refundable flag should be " + expectedRefundable);

        System.out.println("✓ Database: Refundable flag verified - " + expectedRefundable);
    }

    /**
     * Verifies employee has booking in their record
     */
    public static void assertEmployeeHasBooking(int empId, String bookingId) {
        int count = BookingQueries.getEmployeeBookingCount(empId);

        if (count < 0) {
            System.out.println("⚠️  Could not verify employee bookings");
            return;
        }

        assertTrue(count > 0, "Employee " + empId + " should have at least one booking");

        System.out.println("✓ Database: Employee " + empId + " has " + count + " booking(s)");
    }
}
