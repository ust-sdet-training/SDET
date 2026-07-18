package com.tripstack.performance;

import com.tripstack.base.BaseTest;
import com.tripstack.model.request.BookingRequest;
import com.tripstack.model.request.FlightSearchRequest;
import com.tripstack.model.response.BookingResponse;
import com.tripstack.services.AuthService;
import com.tripstack.services.BookingService;
import com.tripstack.services.FlightService;
import com.tripstack.utils.TestDataFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutPerformanceTest extends BaseTest {

    private static final long THRESHOLD_MS = 1500;

    private final AuthService authService = new AuthService();
    private final FlightService flightService = new FlightService();
    private final BookingService bookingService = new BookingService();

    @Test
    @DisplayName("Checkout Performance Baseline")
    void verifyCheckoutPerformance() {

        String token = authService.getToken();

        FlightSearchRequest searchRequest =
                TestDataFactory.flightSearch();

        Response flights =
                flightService.searchFlights(searchRequest);

        flights.then().statusCode(200);

        String flightId =
                flightService.getFirstFlightId(flights);

        Response seatMap =
                flightService.getSeatMap(flightId);

        String seatId =
                flightService.getFirstAvailableSeat(seatMap);

        BookingRequest bookingRequest =
                TestDataFactory.bookingRequest(flightId, seatId);

        BookingResponse booking =
                bookingService.createBooking(token, bookingRequest);

        long total = 0;

        for (int i = 1; i <= 5; i++) {

            long start = System.currentTimeMillis();

            Response payment =
                    bookingService.payBooking(token, booking.getId());

            payment.then().statusCode(200);

            long responseTime =
                    System.currentTimeMillis() - start;

            total += responseTime;

            System.out.println("Run " + i + " : " + responseTime + " ms");

        }

        long average = total / 5;

        System.out.println("--------------------------------");
        System.out.println("Checkout Performance Baseline");
        System.out.println("Average : " + average + " ms");
        System.out.println("Threshold : " + THRESHOLD_MS + " ms");
        System.out.println("--------------------------------");

        assertTrue(
                average < THRESHOLD_MS,
                "Performance regression detected. Average response time = "
                        + average + " ms"
        );

    }

}