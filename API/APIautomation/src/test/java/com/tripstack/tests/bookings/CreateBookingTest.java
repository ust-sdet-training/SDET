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
public class CreateBookingTest extends BaseTest {

    @Test
    void createBookingReturnsHeldState() {
        Response search = busClient.searchBus(token, ConfigManager.FROM_CITY, ConfigManager.TO_CITY, travelDate());
        search.then().statusCode(200);
        String busId = search.jsonPath().getString("buses[0].id");
        assertNotNull(busId, "No buses found for " + ConfigManager.FROM_CITY + "->" + ConfigManager.TO_CITY);

        BookingRequest request = new BookingRequest("bus", busId, List.of("L3"), true);
        Response response = bookingClient.createBooking(token, request);

        response.then()
                .statusCode(anyOf(is(200), is(201)))
                .body(matchesJsonSchemaInClasspath("schemas/booking-schema.json"))
                .body("state", equalTo("HELD"))
                .body("empId", equalTo("1014"))
                .body("id", notNullValue());
    }
}