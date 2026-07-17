package com.tripstack.tests.db;

import com.tripstack.base.BaseTest;
import com.tripstack.config.DatabaseManager;
import com.tripstack.models.BookingRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Tag("db")
public class BookingDBValidationTest extends BaseTest {

    @Test
    void bookingRowMatchesNamespaceAndPnrFormat() throws Exception {
        assumeTrue(System.getenv("TRIPSTACK_DB_URL") != null,
                "Skipping DB test - TRIPSTACK_DB_URL not configured");

        BookingRequest request = new BookingRequest("bus", "BUS-IXCBLR-1", List.of("L3"), true);
        Response created = bookingClient.createBooking(token, request);
        created.then().statusCode(anyOf(is(200), is(201)));
        String bookingId = created.jsonPath().getString("bookingId");

        bookingClient.pay(token, bookingId).then().statusCode(200);
        Response confirmed = bookingClient.confirm(token, bookingId);
        confirmed.then().statusCode(200);
        String pnr = confirmed.jsonPath().getString("pnr");

        assertTrue(pnr.matches("TS-1014-\\d{4}"), "PNR format mismatch: " + pnr);

        try (Connection conn = DatabaseManager.getConnection();
             ResultSet rs = DatabaseManager.findBookingByPnr(conn, pnr)) {

            assertTrue(rs.next(), "No DB row found for PNR " + pnr);
            assertEquals("1014", rs.getString("emp_id"),
                    "Ownership boundary violated: emp_id column must equal 1014");
            assertEquals("CONFIRMED", rs.getString("status"));
        }
    }
}