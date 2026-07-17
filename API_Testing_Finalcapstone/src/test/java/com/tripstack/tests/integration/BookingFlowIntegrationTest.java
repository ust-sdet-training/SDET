package com.tripstack.tests.integration;

import com.tripstack.models.BookingResponse;
import com.tripstack.models.FlightResponse;
import com.tripstack.support.BaseTest;
import com.tripstack.support.TestValidationHelper;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class BookingFlowIntegrationTest extends BaseTest {

    private final TestValidationHelper validationHelper = new TestValidationHelper();

    @Test
    void shouldCompleteBookingFlowAcrossApiAndDatabase() throws Exception {
        FlightResponse[] flights = apiClient.getFlights().as(FlightResponse[].class);
        assertThat(flights.length, equalTo(1));
        assertThat(flights[0].getFlightId(), equalTo("FL123"));

        BookingResponse booking = apiClient.createBooking("FL123", "Asha").as(BookingResponse.class);
        assertThat(booking.getStatus(), equalTo("success"));
        assertThat(booking.getMessage(), notNullValue());

        validationHelper.assertDatabaseIsAvailable();
    }
}
