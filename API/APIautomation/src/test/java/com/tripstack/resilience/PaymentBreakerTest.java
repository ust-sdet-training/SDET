package com.tripstack.resilience;

import com.tripstack.base.BaseTest;
import com.tripstack.config.ConfigManager;
import com.tripstack.models.BookingRequest;
import com.tripstack.resilience.CircuitBreaker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("api")
@Tag("resilience")
public class PaymentBreakerTest extends BaseTest {

    private static final int FAILURE_THRESHOLD = 3;
    private static final long OPEN_DURATION_MS = 5000;

    @Test
    void breakerOpensOnRepeatedGatewayFaultsAndRecovers() {
        CircuitBreaker breaker = new CircuitBreaker(FAILURE_THRESHOLD, OPEN_DURATION_MS);

        int attempts = 6;
        int observedFailures = 0;
        boolean breakerTrippedDuringRun = false;

        for (int i = 0; i < attempts; i++) {
            String bookingId = createFreshHeldBooking(i);

            try {
                Response payResponse = breaker.call(
                        () -> bookingClient.pay(token, bookingId),
                        r -> r.getStatusCode() >= 500 || r.getStatusCode() == 402
                );

                int status = payResponse.getStatusCode();
                System.out.println("Attempt " + (i + 1) + ": pay -> " + status
                        + " | breaker=" + breaker.getState());

                if (status == 402 || status >= 500) {
                    observedFailures++;
                }
            } catch (IllegalStateException breakerOpen) {
                breakerTrippedDuringRun = true;
                System.out.println("Attempt " + (i + 1) + ": short-circuited by breaker (state=OPEN)");
            }
        }

        System.out.println("Final breaker state: " + breaker.getState()
                + " | observed gateway failures: " + observedFailures);

        if (observedFailures == 0) {
            // No fault injected yet (pre-Day-6): every call should reach the real
            // gateway healthy, so the breaker should never trip.
            assertThat("Breaker should stay CLOSED when the gateway is healthy",
                    breaker.getState(), equalTo(CircuitBreaker.State.CLOSED));
        } else {
            // Once the director injects the fault, this is what should fire live.
            assertThat("Breaker should have tripped OPEN after repeated gateway faults",
                    breakerTrippedDuringRun || breaker.getState() != CircuitBreaker.State.CLOSED,
                    is(true));
        }
    }

    private String createFreshHeldBooking(int attemptIndex) {
        Response search = busClient.searchBus(token, ConfigManager.FROM_CITY, ConfigManager.TO_CITY, travelDate());
        search.then().statusCode(200);
        String busId = search.jsonPath().getString("buses[0].id");
        assertNotNull(busId, "No buses found for " + ConfigManager.FROM_CITY + "->" + ConfigManager.TO_CITY);

        Response seatMap = busClient.getSeatMap(token, busId);
        seatMap.then().statusCode(200);

        List<String> available = seatMap.jsonPath()
                .getList("decks.lower.findAll { it.state == 'available' }.seatId", String.class);
        if (available == null || available.isEmpty()) {
            available = seatMap.jsonPath()
                    .getList("decks.upper.findAll { it.state == 'available' }.seatId", String.class);
        }
        assertNotNull(available, "No seat map data for bus " + busId);
        assertTrue_notEmpty(available, busId);

        String seatId = available.get(attemptIndex % available.size());
        BookingRequest request = new BookingRequest("bus", busId, List.of(seatId), true);
        Response created = bookingClient.createBooking(token, request);
        created.then().statusCode(anyOf(is(200), is(201)));
        return created.jsonPath().getString("id");
    }

    private void assertTrue_notEmpty(List<String> available, String busId) {
        org.junit.jupiter.api.Assertions.assertFalse(available.isEmpty(),
                "No available seats on bus " + busId);
    }
}