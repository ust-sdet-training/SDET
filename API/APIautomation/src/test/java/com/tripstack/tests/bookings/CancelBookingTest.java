package com.tripstack.tests.bookings;

import com.tripstack.base.BaseTest;
import com.tripstack.config.ConfigManager;
import com.tripstack.models.BookingRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        created.then()
                .statusCode(anyOf(is(200), is(201)))
                .body(matchesJsonSchemaInClasspath("schemas/booking-schema.json"));
        String bookingId = created.jsonPath().getString("id");

        bookingClient.pay(token, bookingId).then().statusCode(200);
        bookingClient.confirm(token, bookingId).then().statusCode(200);

        Response cancelled = bookingClient.cancel(token, bookingId);
        cancelled.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/booking-response-schema.json"))
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