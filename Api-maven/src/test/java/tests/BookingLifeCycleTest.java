package tests;

import API_FrameWork.models.BookingRequest;
import API_FrameWork.models.BookingResponse;
import API_FrameWork.models.FlightResponse;
import API_FrameWork.models.LoginResponse;
import API_FrameWork.models.SeatMapResponse;
import API_FrameWork.service.AuthService;
import API_FrameWork.service.BookingService;
import API_FrameWork.service.FlightService;
import API_FrameWork.service.SeatService;
import API_FrameWork.support.TestContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class BookingLifeCycleTest {
    @Test
    void bookingLifeCycle() {
        // ================= Login =================
        AuthService authService = new AuthService();
        LoginResponse loginResponse = authService.login(
                "karl@tripstack.test",
                "Password@123"
        );
        TestContext.token = loginResponse.getToken();
        // ================= Search Flight =================
        FlightService flightService = new FlightService();
        FlightResponse flights = flightService.searchFlights(
                "JAI",
                "BOM",
                "2026-08-14",
                "economy"
        );
        TestContext.flightId = flights.getFlights().get(0).getId();
        // ================= Seat Map =================
        SeatService seatService = new SeatService();
        SeatMapResponse seatMap = seatService.getSeatMap(TestContext.flightId);
        outer:
        for (SeatMapResponse.Row row : seatMap.getRows()) {
            for (SeatMapResponse.Seat seat : row.getSeats()) {
                if (!seat.isOccupied()) {
                    TestContext.seatId = seat.getSeatId();
                    break outer;
                }
            }
        }
        Assertions.assertNotNull(TestContext.seatId);
        // ================= Create Booking =================
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setJourneyType("flight");
        bookingRequest.setInventoryId(TestContext.flightId);
        bookingRequest.setSeatIds(Collections.singletonList(TestContext.seatId));
        bookingRequest.setRefundable(true);
        bookingRequest.setHoldTtlSec(120);
        BookingService bookingService = new BookingService();
        BookingResponse bookingResponse = bookingService.createBooking(bookingRequest);
        Assertions.assertEquals("HELD", bookingResponse.getState());
        TestContext.bookingId = bookingResponse.getId();
        // ================= Pay Booking =================
        bookingResponse = bookingService.payBooking(TestContext.bookingId);
        Assertions.assertEquals(
                "PAYMENT_PENDING",
                bookingResponse.getState()
        );
        // ================= Confirm Booking =================
        bookingResponse = bookingService.confirmBooking(TestContext.bookingId);
        Assertions.assertEquals(
                "CONFIRMED",
                bookingResponse.getState()
        );
        TestContext.pnr = bookingResponse.getPnr();
        Assertions.assertNotNull(TestContext.pnr);
        System.out.println("=====================================");
        System.out.println("Booking ID : " + TestContext.bookingId);
        System.out.println("PNR        : " + TestContext.pnr);
        System.out.println("=====================================");
    }
}