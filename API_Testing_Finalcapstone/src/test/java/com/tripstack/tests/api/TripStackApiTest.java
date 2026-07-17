package com.tripstack.tests.api;

import com.tripstack.models.BookingResponse;
import com.tripstack.models.FlightResponse;
import com.tripstack.support.BaseTest;
import com.tripstack.support.TestValidationHelper;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

class TripStackApiTest extends BaseTest {

    private final TestValidationHelper validationHelper = new TestValidationHelper();

    @Test
    void shouldReturnFlightsList() throws IOException, Exception {
        Response response = apiClient.getFlights();

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.jsonPath().getList("$"), not(empty()));

        FlightResponse[] flights = response.as(FlightResponse[].class);
        assertThat(flights[0].getFlightId(), equalTo("FL123"));

        validationHelper.assertJsonSchema(response, "flight-schema.json");
        validationHelper.assertDatabaseIsAvailable();
    }

    @Test
    void shouldCreateBookingSuccessfully() throws IOException, Exception {
        Response response = apiClient.createBooking("FL123", "Asha");

        assertThat(response.getStatusCode(), anyOf(equalTo(200), equalTo(201)));
        assertThat(response.jsonPath().getString("status"), equalTo("success"));

        BookingResponse booking = response.as(BookingResponse.class);
        assertThat(booking.getStatus(), equalTo("success"));

        validationHelper.assertJsonSchema(response, "booking-schema.json");
        validationHelper.assertDatabaseIsAvailable();
    }
}
