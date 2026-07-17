package com.capstone.tests;

import com.capstone.api.BookingApiClient;
import com.capstone.api.FlightApiClient;
import com.capstone.data.db.BookingRecord;
import com.capstone.data.db.BookingRepository;
import com.capstone.data.db.DbConfig;
import com.capstone.support.BaseApiTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingDbTest extends BaseApiTest {

    private final BookingRepository bookingRepository = new BookingRepository();
    private FlightApiClient flightApiClient;
    private BookingApiClient bookingApiClient;

    @BeforeEach
    void initClients() {
        flightApiClient = new FlightApiClient(request);
        bookingApiClient = new BookingApiClient(request);
    }

    @Test
    void shouldPersistConfirmedFlightBookingWithApiValues() throws SQLException {
        assumeDatabaseIsReachable();

        String token = loginAs("traveller");
        String flightId = flightApiClient.selectFirstFlightId("CCU", "BOM", "2026-08-10", 1, "economy");
        List<String> availableSeatIds = flightApiClient.selectAvailableSeatIds(flightId, "economy");

        Response holdResponse = bookingApiClient.holdAvailableSeat(token, flightId, availableSeatIds);
        assertEquals(201, holdResponse.statusCode());
        String bookingId = holdResponse.jsonPath().getString("id");

        Response payResponse = bookingApiClient.payBooking(token, bookingId);
        assertEquals(200, payResponse.statusCode());

        Response confirmResponse = bookingApiClient.confirmBooking(token, bookingId);
        assertEquals(200, confirmResponse.statusCode());

        BookingRecord databaseBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new AssertionError("Confirmed booking was not persisted in the database"));

        assertAll(
                () -> assertEquals(bookingId, databaseBooking.id()),
                () -> assertEquals(confirmResponse.jsonPath().getString("pnr"), databaseBooking.pnr()),
                () -> assertEquals(confirmResponse.jsonPath().getString("empId"), databaseBooking.empId()),
                () -> assertEquals("flight", databaseBooking.journeyType()),
                () -> assertEquals(flightId, databaseBooking.inventoryId()),
                () -> assertEquals("CONFIRMED", databaseBooking.state()),
                () -> assertEquals(confirmResponse.jsonPath().getInt("amountPaise"), databaseBooking.amountPaise()),
                () -> assertEquals(confirmResponse.jsonPath().getBoolean("refundable"), databaseBooking.refundable())
        );
    }

    private void assumeDatabaseIsReachable() {
        try (Connection ignored = DbConfig.connection()) {
            // The query is executed only when the configured database is available.
        } catch (SQLException exception) {
            Assumptions.assumeTrue(false,
                    "Database is not reachable or is not configured for this API environment: " + exception.getMessage());
        }
    }
}
