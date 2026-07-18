package org.sdet.tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sdet.API_Framework.builders.BookingBuilder;
import org.sdet.API_Framework.builders.FlightSearchBuilder;
import org.sdet.API_Framework.builders.LoginBuilder;
import org.sdet.API_Framework.clients.AuthClient;
import org.sdet.API_Framework.clients.BookingClient;
import org.sdet.API_Framework.clients.FlightClient;
import org.sdet.API_Framework.clients.PaymentClient;
import org.sdet.API_Framework.model.request.BookingRequest;
import org.sdet.API_Framework.model.response.LoginResponse;
import org.sdet.API_Framework.utils.TokenManager;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BookingLifeCycleTests extends BaseTest {

    private final AuthClient authClient = new AuthClient();
    private final FlightClient flightClient = new FlightClient();
    private final BookingClient bookingClient = new BookingClient();
    private final PaymentClient paymentClient = new PaymentClient();

    @BeforeEach
    void login() {

        Response loginResponse =
                authClient.login(LoginBuilder.validLogin());

        TokenManager.setToken(
                loginResponse.as(LoginResponse.class).getToken()
        );
    }

    @Test
    @DisplayName("Verify complete booking lifecycle")
    void shouldCompleteBookingLifeCycle() {

        // Search Flights
        Response flightResponse =
                flightClient.searchFlights(
                        FlightSearchBuilder.validSearch()
                );

        assertEquals(200, flightResponse.getStatusCode());

        JsonPath flightJson = flightResponse.jsonPath();

        String flightId =
                flightJson.getString("flights[0].id");

        assertNotNull(flightId);

        // Get Seat Map
        Response seatResponse =
                flightClient.getSeatMap(flightId);

        assertEquals(200, seatResponse.getStatusCode());

        JsonPath seatJson = seatResponse.jsonPath();

        String seatNo = null;

        List<Map<String, Object>> rows =
                seatJson.getList("rows");

        assertNotNull(rows);
        assertFalse(rows.isEmpty());

        outer:
        for (Map<String, Object> row : rows) {

            List<Map<String, Object>> seats =
                    (List<Map<String, Object>>) row.get("seats");

            if (seats == null) {
                continue;
            }

            for (Map<String, Object> seat : seats) {

                Boolean occupied =
                        (Boolean) seat.get("occupied");

                if (Boolean.FALSE.equals(occupied)) {

                    seatNo =
                            seat.get("seat_id").toString();

                    break outer;
                }
            }
        }

        assertNotNull(seatNo, "No available seat found.");

        // Create Booking
        BookingRequest bookingRequest =
                BookingBuilder.createBooking(
                        flightId,
                        seatNo
                );

        Response bookingResponse =
                bookingClient.createBooking(
                        bookingRequest
                );

        assertEquals(201, bookingResponse.getStatusCode());

        JsonPath bookingJson =
                bookingResponse.jsonPath();

        String bookingId =
                bookingJson.getString("id");

        assertNotNull(bookingId);

        // Payment
        Response paymentResponse =
                paymentClient.pay(bookingId);

        assertEquals(200, paymentResponse.getStatusCode());

        // Confirm Booking
        Response confirmResponse =
                bookingClient.confirmBooking(
                        bookingId
                );

        assertEquals(200, confirmResponse.getStatusCode());

        JsonPath confirmJson =
                confirmResponse.jsonPath();

        String pnr =
                confirmJson.getString("pnr");

        assertNotNull(pnr);

        // Fetch Booking
        Response finalBooking =
                bookingClient.getBookingByPNR(pnr);

        assertEquals(200, finalBooking.getStatusCode());

        JsonPath finalJson =
                finalBooking.jsonPath();

        assertEquals(
                "CONFIRMED",
                finalJson.getString("state")
        );

        assertEquals(
                flightId,
                finalJson.getString("inventoryId")
        );

        List<String> bookedSeats =
                finalJson.getList("seatIds");

        assertNotNull(bookedSeats);

        assertTrue(
                bookedSeats.contains(seatNo)
        );
    }
}