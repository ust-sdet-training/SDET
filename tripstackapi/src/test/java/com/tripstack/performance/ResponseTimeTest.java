package com.tripstack.performance;

import com.tripstack.base.BaseTest;
import com.tripstack.services.AuthService;
import com.tripstack.services.FlightService;
import com.tripstack.utils.TestDataFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResponseTimeTest extends BaseTest {

    AuthService authService = new AuthService();
    FlightService flightService = new FlightService();

    @Test
    void verifyFlightSearchResponseTime() {

        long start = System.currentTimeMillis();

        Response response =
                flightService.searchFlights(
                        TestDataFactory.flightSearch());

        long end = System.currentTimeMillis();

        response.then().statusCode(200);

        assertTrue((end - start) < 3000);

    }

    @Test
    void verifyLoginResponseTime() {

        long start = System.currentTimeMillis();

        authService.getToken();

        long end = System.currentTimeMillis();

        assertTrue((end - start) < 3000);

    }

}