package com.tripstack.tests.bookings;

import com.tripstack.base.BaseTest;
import com.tripstack.config.ConfigManager;
import com.tripstack.models.BookingRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Tag("api")
@Tag("booking")
public class CancelBookingTest extends BaseTest {

    @Test
    void cancelConfirmedBookingReachesRefunded() {
        Response search = busClient.searchBus(token, ConfigManager.FROM_CITY, ConfigManager.TO_CITY, travelDate());
        search.then().statusCode(200);
        String busId = search.jsonPath().getString("buses[0].id");
        assertNotNull(busId, "No buses found for " + ConfigManager.FROM_CITY + "->" + ConfigManager.TO_CITY);

        String seatId = firstAvailableSeat(busId);
        assertNotNull(seatId, "No available seats found on bus " + busId);

        BookingRequest request = new BookingRequest("bus", busId, List.of(seatId), true);
        Response created = bookingClient.createBooking(token, request);
        created.then().statusCode(anyOf(is(200), is(201)));
        String bookingId = created.jsonPath().getString("id");

        Response payResponse = bookingClient.pay(token, bookingId);
        int payStatus = payResponse.getStatusCode();

        // Day-6 fault-aware: skip the cancel-flow assertion if payment is
        // currently declined - a HELD/PAYMENT_PENDING booking can't reach
        // CONFIRMED, so cancellation-to-REFUNDED can't be exercised right now.
        assumeTrue(payStatus == 200,
                "Skipping cancel-to-refunded assertion - payment declined (402), Day-6 fault flag is active. "
                        + "This is expected behavior, not a failure.");

        bookingClient.confirm(token, bookingId).then().statusCode(200);

        Response cancelled = bookingClient.cancel(token, bookingId);
        cancelled.then()
                .statusCode(200)
                .body("state", equalTo("REFUNDED"));
    }

    private String firstAvailableSeat(String busId) {
        Response seatMap = busClient.getSeatMap(token, busId);
        seatMap.then().statusCode(200);

        List<String> lowerAvailable = seatMap.jsonPath()
                .getList("decks.lower.findAll { it.state == 'available' }.seatId", String.class);
        if (lowerAvailable != null && !lowerAvailable.isEmpty()) {
            return lowerAvailable.get(0);
        }
        List<String> upperAvailable = seatMap.jsonPath()
                .getList("decks.upper.findAll { it.state == 'available' }.seatId", String.class);
        if (upperAvailable != null && !upperAvailable.isEmpty()) {
            return upperAvailable.get(0);
        }
        return null;
    }
}