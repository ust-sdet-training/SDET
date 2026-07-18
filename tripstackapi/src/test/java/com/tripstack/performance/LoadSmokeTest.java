package com.tripstack.performance;

import com.tripstack.base.BaseTest;
import com.tripstack.services.FlightService;
import com.tripstack.utils.TestDataFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoadSmokeTest extends BaseTest {

    FlightService flightService = new FlightService();

    @Test
    void verifySearchLoad() {

        long total = 0;

        for (int i = 0; i < 10; i++) {

            long start = System.currentTimeMillis();

            flightService.searchFlights(
                            TestDataFactory.flightSearch())
                    .then()
                    .statusCode(200);

            total += System.currentTimeMillis() - start;

        }

        long average = total / 10;

        System.out.println("Average Response Time : " + average + " ms");

        assertTrue(average < 3000);

    }

}