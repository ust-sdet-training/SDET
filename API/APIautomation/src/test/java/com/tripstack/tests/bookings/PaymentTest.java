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
@Tag("resilience")
public class PaymentTest extends BaseTest {

    @Test
    void paymentReflectsInjectedGatewayDecline() {
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

        Response payResponse = bookingClient.pay(token, bookingId);
        int status = payResponse.getStatusCode();

        if (status == 402) {
            payResponse.then().body("error", equalTo("GATEWAY_DECLINE"));
        } else {
            payResponse.then()
                    .statusCode(200)
                    .body(matchesJsonSchemaInClasspath("schemas/booking-response-schema.json"));
        }
    }
}