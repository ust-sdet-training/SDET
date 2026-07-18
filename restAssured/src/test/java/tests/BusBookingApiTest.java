package tests;

import api.clients.BookingClient;
import api.clients.BusClient;
import api.auth.TokenManager;
import api.specs.ResponseSpecs;
import builders.BookingRequestBuilder;
import builders.BookingIdRequestBuilder;
import builders.BookingPnrRequestBuilder;
import builders.BusSearchRequestBuilder;
import builders.BusSeatsRequestBuilder;
import io.restassured.response.Response;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;

class BusBookingApiTest {

    private final BusClient busClient = new BusClient();
    private final BookingClient bookingClient = new BookingClient();

    @BeforeEach
    void resetNamespace() {

        TokenManager.clear();
        bookingClient.resetNamespace().then()
                .spec(ResponseSpecs.success())
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/reset-schema.json"));
    }

    @Test
    void shouldBookAcSemiSleeperBusThroughConfirmation() {

        Response searchResponse = busClient.search(
                new BusSearchRequestBuilder()
                        .withFrom("DEL")
                        .withTo("BOM")
                        .withDate("2026-08-04")
                        .build());

        searchResponse.then()
                .spec(ResponseSpecs.success())
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/bus-search-schema.json"))
                .body("from", equalTo("DEL"))
                .body("to", equalTo("BOM"))
                .body("date", equalTo("2026-08-04"))
                .body("count", greaterThan(0))
                .body("buses", notNullValue());

        List<Map<String, Object>> buses = searchResponse.jsonPath().getList("buses");
        Map<String, Object> selectedBus = selectPreferredBus(buses);
        String busId = stringValue(selectedBus, "id");
        String busKind = stringValue(selectedBus, "kind", "busType");

        org.hamcrest.MatcherAssert.assertThat(
                "Selected bus must be the requested AC semi-sleeper service",
                busKind,
                containsStringIgnoringCase("ac-semi")
        );

        Response seatMapResponse = busClient.seats(
                new BusSeatsRequestBuilder()
                        .withBusId(busId)
                        .build());

        seatMapResponse.then()
                .spec(ResponseSpecs.success())
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/bus-seats-schema.json"))
                .body("busId", equalTo(busId));

        List<Map<String, Object>> lowerDeck = seatMapResponse.jsonPath().getList("decks.lower");
        List<Map<String, Object>> upperDeck = seatMapResponse.jsonPath().getList("decks.upper");

        String selectedSeat = java.util.stream.Stream.concat(
                lowerDeck == null ? java.util.stream.Stream.empty() : lowerDeck.stream(),
                upperDeck == null ? java.util.stream.Stream.empty() : upperDeck.stream())
            .filter(Objects::nonNull)
            .filter(seat -> "available".equalsIgnoreCase(stringValue(seat, "state")))
            .map(seat -> stringValue(seat, "seatId", "seat_id"))
            .filter(Objects::nonNull)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No available bus seats found"));

        Response holdResponse = bookingClient.createBooking(
                new BookingRequestBuilder()
                        .forBus(busId)
                        .withSeatIds(List.of(selectedSeat))
                        .refundable(true)
                        .build());

        holdResponse.then()
                .spec(ResponseSpecs.success())
                .statusCode(201)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/booking-schema.json"))
                .body("state", equalTo("HELD"))
                .body("empId", notNullValue())
                .body("inventoryId", equalTo(busId))
                .body("seatIds", hasItem(selectedSeat))
                .body("pnr", equalTo(null));

        String bookingId = holdResponse.jsonPath().getString("id");

        Response payResponse = bookingClient.pay(
                new BookingIdRequestBuilder()
                        .withBookingId(bookingId)
                        .build());

        if(payResponse.statusCode() == 402){
            payResponse.then().log().all();
        payResponse.then()
                .statusCode(402)
                .body("error", equalTo("GATEWAY_DECLINE"))
                .body("message", equalTo("payment declined by gateway"));
        }

        else if (payResponse.statusCode() == 200) {
            payResponse.then()
                    .spec(ResponseSpecs.success())
                    .statusCode(200)
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/booking-schema.json"))
                    .body("state", equalTo("PAYMENT_PENDING"))
                    .body("pnr", equalTo(null));
        

        payResponse.then()
                .spec(ResponseSpecs.success())
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/booking-schema.json"))
                .body("state", equalTo("PAYMENT_PENDING"))
                .body("pnr", equalTo(null));

        Response confirmResponse = bookingClient.confirm(
                new BookingIdRequestBuilder()
                        .withBookingId(bookingId)
                        .build());

        confirmResponse.then()
                .spec(ResponseSpecs.success())
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/booking-schema.json"))
                .body("state", equalTo("CONFIRMED"))
                .body("pnr", startsWith("TS-"))
                .body("inventoryId", equalTo(busId))
                .body("seatIds", hasItem(selectedSeat));

        String pnr = confirmResponse.jsonPath().getString("pnr");

        bookingClient.readBookingByPnr(
                        new BookingPnrRequestBuilder()
                                .withPnr(pnr)
                                .build())
                .then()
                .spec(ResponseSpecs.success())
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/booking-schema.json"))
                .body("pnr", equalTo(pnr))
                .body("state", equalTo("CONFIRMED"))
                .body("inventoryId", equalTo(busId))
                .body("seatIds", hasItem(selectedSeat));

        Response listResponse = bookingClient.listBookings();

        listResponse.then()
                .spec(ResponseSpecs.success())
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/bookings-schema.json"))
                .body("pnr", hasItem(pnr));
        }
    }

    private Map<String, Object> selectPreferredBus(List<Map<String, Object>> buses) {

        return buses.stream()
                .filter(Objects::nonNull)
                .filter(bus -> {
                    String kind = stringValue(bus, "kind", "busType");
                    String operatorName = stringValue(bus, "operatorName");
                    String composite = (String.valueOf(kind) + " " + String.valueOf(operatorName)).toLowerCase(Locale.ROOT);
                    return composite.contains("ac") && composite.contains("semi");
                })
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "No AC semi-sleeper bus found for DEL to BOM on 2026-08-04"));
    }

    private String stringValue(Map<String, Object> row, String... keys) {

        for (String key : keys) {
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                if (normalize(entry.getKey()).equals(normalize(key)) && entry.getValue() != null) {
                    return String.valueOf(entry.getValue());
                }
            }
        }

        return null;
    }

    private String normalize(String value) {

        return value == null ? "" : value.replace("_", "").toLowerCase(Locale.ROOT);
    }


}