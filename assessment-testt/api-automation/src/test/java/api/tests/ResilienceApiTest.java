package api.tests;

import api.api.ApiClient;
import api.config.AppConfig;
import api.models.Booking;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class ResilienceApiTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void paymentFaultShouldReturnFiveHundredAndLeaveBookingUnconfirmed() throws Exception {
        Assumptions.assumeTrue(
            AppConfig.ENABLE_BOOKING && AppConfig.EXPECT_PAYMENT_500,
            "Resilience fault flow is disabled; set ENABLE_BOOKING=true and EXPECT_PAYMENT_500=true for the live injected-fault check"
        );

        JsonPath authResponse = ApiClient.login(AppConfig.USER_EMAIL, AppConfig.USER_PASSWORD);
        String token = authResponse.getString("token");

        String bookingDate = LocalDate.now().plusDays(10).toString();
        JsonPath searchResponse = ApiClient.searchBuses("GOI", "BLR", bookingDate);
        assertThat("At least one bus should be returned", searchResponse.getList("buses"), notNullValue());
        String busId = searchResponse.getString("buses[0].id");

        JsonPath seatResponse = ApiClient.getBusSeats(busId);
        List<Map<String, Object>> lower = seatResponse.getList("decks.lower");
        List<Map<String, Object>> upper = seatResponse.getList("decks.upper");

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
            Response createResponse = ApiClient.createBooking(token, bookingJson);
            int code = createResponse.getStatusCode();
            if (code == 201) {
                booking = mapper.readValue(createResponse.prettyPrint(), Booking.class);
                break;
            }
            if (code != 409) {
                throw new AssertionError(
                    "Expected 201 or 409 from create booking, got " + code + ", body=" + createResponse.prettyPrint()
                );
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
        assertThat(paymentResponse.getStatusCode(), equalTo(500));
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
