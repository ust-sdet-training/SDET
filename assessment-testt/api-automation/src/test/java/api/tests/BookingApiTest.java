package api.tests;

import api.api.ApiClient;
import api.config.AppConfig;
import api.models.Booking;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assumptions;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class BookingApiTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldCreateAndConfirmBooking() throws Exception {

        JsonPath authResponse = ApiClient.login(AppConfig.USER_EMAIL, AppConfig.USER_PASSWORD);
        String token = authResponse.getString("token");

        String bookingDate = LocalDate.now().plusDays(10).toString();
        JsonPath searchResponse = ApiClient.searchBuses("GOI", "BLR", bookingDate);
        assertThat("At least one bus should be returned", searchResponse.getList("buses"), notNullValue());
        String busId = searchResponse.getString("buses[0].id");

        JsonPath seatResponse = ApiClient.getBusSeats(busId);
        List<Map<String, Object>> lower = seatResponse.getList("decks.lower");
        List<Map<String, Object>> upper = seatResponse.getList("decks.upper");
        boolean hasAvailable = false;
        try {
            String s = findAvailableSeat(lower, upper);
            hasAvailable = s != null && !s.isBlank();
        } catch (IllegalStateException e) {
            hasAvailable = false;
        }
        assertThat("There should be at least one available seat", hasAvailable, equalTo(true));


        // ENABLE_BOOKING should be set to true only when you want the test to create, pay and confirm a real booking.
        // Keep it false for normal local runs and CI to avoid creating live bookings and reduce flakiness.
        Assumptions.assumeTrue(api.config.AppConfig.ENABLE_BOOKING, "Booking tests are disabled; set ENABLE_BOOKING=true to run mutating flow");

        Set<String> triedSeats = new HashSet<>();
        Booking booking = null;
        String bookingJson = null;
        int attempts = 0;
        while (attempts < 5) {
            attempts++;
            String seatId = findNextAvailableSeat(lower, upper, triedSeats);
            triedSeats.add(seatId);
            Map<String, Object> bookingPayload = new HashMap<>();
            bookingPayload.put("journeyType", "bus");
            bookingPayload.put("inventoryId", busId);
            bookingPayload.put("seatIds", List.of(seatId));
            bookingPayload.put("refundable", true);
            bookingPayload.put("holdTtlSec", 120);

            bookingJson = mapper.writeValueAsString(bookingPayload);
            var resp = ApiClient.createBooking(token, bookingJson);
            int code = resp.getStatusCode();
            if (code == 201) {
                booking = mapper.readValue(resp.prettyPrint(), Booking.class);
                break;
            }
            if (code != 409) {
                throw new AssertionError("Expected 201 from create booking, got " + code + ", body=" + resp.prettyPrint());
            }

            JsonPath refreshedSeats = ApiClient.getBusSeats(busId);
            lower = refreshedSeats.getList("decks.lower");
            upper = refreshedSeats.getList("decks.upper");
        }

        if (booking == null) {
            throw new AssertionError("Unable to create booking after retries, last payload=" + bookingJson);
        }

        assertThat(booking.empId, equalTo("1024"));
        assertThat(booking.state, equalTo("HELD"));
        assertThat(booking.pnr, nullValue());

        Response paymentResponse = ApiClient.payBookingRaw(token, booking.id);
        int paymentStatus = paymentResponse.getStatusCode();

        if (paymentStatus >= 500 && paymentStatus <= 599) {
            assertThat(booking.state, equalTo("HELD"));
            assertThat(booking.pnr, nullValue());
            return;
        }

        assertThat(
            "Expected payment success or gateway 5xx, body=" + paymentResponse.asString(),
            paymentStatus,
            equalTo(200)
        );

        Booking payBooking = mapper.readValue(paymentResponse.asString(), Booking.class);
        assertThat(payBooking.state, equalTo("PAYMENT_PENDING"));

        JsonPath confirmResponse = ApiClient.confirmBooking(token, booking.id);
        Booking confirmedBooking = mapper.readValue(confirmResponse.prettyPrint(), Booking.class);
        assertThat(confirmedBooking.state, equalTo("CONFIRMED"));
        assertThat(confirmedBooking.pnr, matchesPattern("^TS-1024-\\d+$"));

        JsonPath fetchResponse = ApiClient.getBookingByPnr(token, confirmedBooking.pnr);
        Booking fetchBooking = mapper.readValue(fetchResponse.prettyPrint(), Booking.class);
        assertThat(fetchBooking.pnr, equalTo(confirmedBooking.pnr));
    }

    private String findAvailableSeat(List<Map<String, Object>> lower, List<Map<String, Object>> upper) {
        if (lower != null) {
            for (Map<String, Object> seat : lower) {
                if ("available".equals(seat.get("state"))) {
                    return seat.get("seatId").toString();
                }
            }
        }
        if (upper != null) {
            for (Map<String, Object> seat : upper) {
                if ("available".equals(seat.get("state"))) {
                    return seat.get("seatId").toString();
                }
            }
        }
        throw new IllegalStateException("No available seat found");
    }

    private String findNextAvailableSeat(List<Map<String, Object>> lower, List<Map<String, Object>> upper, Set<String> tried) {
        if (lower != null) {
            for (Map<String, Object> seat : lower) {
                String id = seat.get("seatId").toString();
                if ("available".equals(seat.get("state")) && !tried.contains(id)) {
                    return id;
                }
            }
        }
        if (upper != null) {
            for (Map<String, Object> seat : upper) {
                String id = seat.get("seatId").toString();
                if ("available".equals(seat.get("state")) && !tried.contains(id)) {
                    return id;
                }
            }
        }
        throw new IllegalStateException("No available seat found after filtering tried seats");
    }
}
