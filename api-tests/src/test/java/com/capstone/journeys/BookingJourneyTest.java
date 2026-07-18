package com.capstone.journeys;

import com.capstone.config.EnvConfig;
import com.capstone.models.BookingRequest;
import com.capstone.models.BookingResponse;
import com.capstone.support.BaseApiTest;
import com.capstone.support.BaseData;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("journey")
public class BookingJourneyTest extends BaseApiTest {

    @Test
    void booksGoiToPunForEmployee() {
        String token = authClient.login(EnvConfig.require("TEST_USER_EMAIL"), EnvConfig.require("TEST_USER_PASSWORD"));

        BookingRequest request = new BookingRequest(
                BaseData.ROUTE,
                BaseData.JOURNEY,
                BaseData.TRAVEL_DATE,
                BaseData.EMPLOYEE_ID
        );

        Response response = bookingClient.createBooking(token, request);
        response.then().statusCode(201);

        BookingResponse booking = response.as(BookingResponse.class);

        assertEquals("HELD", booking.getStatus());
        assertNotNull(booking.getPnr());
    }
}