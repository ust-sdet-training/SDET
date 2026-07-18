package com.capstone.db;

import com.capstone.config.EnvConfig;
import com.capstone.models.BookingRequest;
import com.capstone.support.BaseApiTest;
import com.capstone.support.BaseData;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("db")
public class BookingPersistenceTest extends BaseApiTest {

    private final DbClient dbClient = new DbClient();

    @Test
    void bookingIsPersistedAfterApiCreation() throws Exception {
        int countBefore = dbClient.countBookings();

        String token = authClient.login(EnvConfig.require("TEST_USER_EMAIL"), EnvConfig.require("TEST_USER_PASSWORD"));
        BookingRequest request = new BookingRequest(
                BaseData.ROUTE,
                BaseData.JOURNEY,
                BaseData.TRAVEL_DATE,
                BaseData.EMPLOYEE_ID
        );
        bookingClient.createBooking(token, request).then().statusCode(201);

        int countAfter = dbClient.countBookings();

        assertEquals(countBefore + 1, countAfter);
    }
}