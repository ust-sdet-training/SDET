package sdet.com.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import sdet.com.models.BookingResponse;
import sdet.com.services.TripStackApiService;
import sdet.com.support.TripStackConfig;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

public class TripStackApiFlowTest {

    TripStackApiService api = new TripStackApiService();

    @Test
    void shouldBookBus() {

        String token =
                api.login(
                        TripStackConfig.BASE_URL,
                        TripStackConfig.LOGIN_EMAIL,
                        TripStackConfig.LOGIN_PASSWORD);

        Response me =
                api.me(
                        TripStackConfig.BASE_URL,
                        token);

        assertThat(me.statusCode()).isEqualTo(200);

        Response buses =
                api.searchBuses(
                        TripStackConfig.BASE_URL,
                        token,
                        TripStackConfig.FROM,
                        TripStackConfig.TO,
                        TripStackConfig.JOURNEY_DATE);

        buses.then()
                .body(matchesJsonSchemaInClasspath(
                        "schemas/bus-search-schema.json"));

        assertThat(buses.statusCode()).isEqualTo(200);

        String busId =
                buses.jsonPath().getString(
                        "buses.find { it.id=='BUS-DELIXC-01' }.id");

        Response seatMap =
                api.getBusSeats(
                        TripStackConfig.BASE_URL,
                        token,
                        busId);

        seatMap.then()
                .body(matchesJsonSchemaInClasspath(
                        "schemas/seat-schema.json"));

        String seat =
                api.firstAvailableSeat(seatMap);

        BookingResponse booking =
                api.holdBusBooking(
                        TripStackConfig.BASE_URL,
                        token,
                        busId,
                        seat);

        api.getLastResponse().then()
                .body(matchesJsonSchemaInClasspath(
                        "schemas/booking-schema.json"));

        assertThat(booking.getState())
                .isEqualTo("HELD");

        BookingResponse payment =
                api.payBooking(
                        TripStackConfig.BASE_URL,
                        token,
                        booking.getId());

        api.getLastResponse().then()
                .body(matchesJsonSchemaInClasspath(
                        "schemas/booking-schema.json"));

        assertThat(payment.getState())
                .isEqualTo("PAYMENT_PENDING");

        BookingResponse confirm =
                api.confirmBooking(
                        TripStackConfig.BASE_URL,
                        token,
                        booking.getId());

        api.getLastResponse().then()
                .body(matchesJsonSchemaInClasspath(
                        "schemas/booking-schema.json"));

        assertThat(confirm.getState())
                .isEqualTo("CONFIRMED");

        assertThat(confirm.getPnr())
                .isNotBlank();

        Response bookingList =
                api.listBookings(
                        TripStackConfig.BASE_URL,
                        token);

        assertThat(bookingList.statusCode())
                .isEqualTo(200);

        assertThat(bookingList.asString())
                .contains(confirm.getPnr());
    }

}