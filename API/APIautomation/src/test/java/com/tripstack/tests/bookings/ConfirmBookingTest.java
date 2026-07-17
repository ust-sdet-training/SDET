package com.tripstack.tests.bookings;

import com.tripstack.base.BaseTest;
import com.tripstack.config.ConfigManager;
import com.tripstack.models.BookingRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("api")
@Tag("booking")
public class ConfirmBookingTest extends BaseTest {

    private static final Pattern PNR_PATTERN = Pattern.compile("^TS-1014-\\d{4}$");

    @Test
    void confirmBookingGeneratesValidPnr() {
        Response search = busClient.searchBus(token, ConfigManager.FROM_CITY, ConfigManager.TO_CITY, travelDate());
        search.then().statusCode(200);
        String busId = search.jsonPath().getString("buses[0].id");
        assertNotNull(busId, "No buses found for " + ConfigManager.FROM_CITY + "->" + ConfigManager.TO_CITY);

        BookingRequest request = new BookingRequest("bus", busId, List.of("L3"), true);
        Response created = bookingClient.createBooking(token, request);
        created.then()
                .statusCode(anyOf(is(200), is(201)))
                .body(matchesJsonSchemaInClasspath("schemas/booking-schema.json"));
        String bookingId = created.jsonPath().getString("id");

        bookingClient.pay(token, bookingId).then().statusCode(200);

        Response confirmed = bookingClient.confirm(token, bookingId);
        confirmed.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/booking-response-schema.json"))
                .body("state", equalTo("CONFIRMED"));

        String pnr = confirmed.jsonPath().getString("pnr");
        assertTrue(PNR_PATTERN.matcher(pnr).matches(), "PNR format invalid: " + pnr);
    }
}