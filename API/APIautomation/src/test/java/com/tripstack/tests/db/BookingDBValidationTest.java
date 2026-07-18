package com.tripstack.tests.db;

import com.tripstack.base.BaseTest;
import com.tripstack.config.ConfigManager;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Tag("db")
public class BookingDBValidationTest extends BaseTest {

    @Test
    void bookingRowMatchesNamespaceAndPnrFormat() throws Exception {
        String dbUrl = System.getenv("TRIPSTACK_DB_URL");
        assumeTrue(dbUrl != null && !dbUrl.isBlank(),
                "Skipping DB test - TRIPSTACK_DB_URL not configured");

        Response search = busClient.searchBus(token, ConfigManager.FROM_CITY, ConfigManager.TO_CITY, travelDate());
        search.then().statusCode(200);
        String busId = search.jsonPath().getString("buses[0].id");
        assertNotNull(busId, "No buses found for " + ConfigManager.FROM_CITY + "->" + ConfigManager.TO_CITY);

        BookingRequest request = new BookingRequest("bus", busId, List.of("L3"), true);
        Response created = bookingClient.createBooking(token, request);
        created.then().statusCode(anyOf(is(200), is(201)));
        String bookingId = created.jsonPath().getString("id");

        bookingClient.pay(token, bookingId).then().statusCode(200);

        Response confirmed = bookingClient.confirm(token, bookingId);
        confirmed.then()
                .statusCode(200)
                .body("state", equalTo("CONFIRMED"));

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