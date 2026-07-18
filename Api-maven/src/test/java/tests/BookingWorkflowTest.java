package tests;

import API_FrameWork.config.TestData;
import API_FrameWork.models.BookingRequest;
import API_FrameWork.models.BookingResponse;
import API_FrameWork.models.FlightResponse;
import API_FrameWork.models.LoginResponse;
import API_FrameWork.models.SeatMapResponse;
import API_FrameWork.service.AuthService;
import API_FrameWork.service.BookingService;
import API_FrameWork.service.FlightService;
import API_FrameWork.service.SeatService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class BookingWorkflowTest extends BaseTest {

    @Test
    @DisplayName("Verify complete booking workflow")
    void bookingWorkflow() {
        AuthService authService = new AuthService();
        LoginResponse loginResponse = authService.login(TestData.EMAIL, TestData.PASSWORD);
        String token = loginResponse.getToken();

        FlightService flightService = new FlightService();
        FlightResponse flights = flightService.searchFlights(
                TestData.FROM,
                TestData.TO,
                TestData.DATE,
                TestData.TRAVEL_CLASS
        );
        String flightId = flights.getFlights().get(0).getId();

        SeatService seatService = new SeatService();
        SeatMapResponse seatMap = seatService.getSeatMap(flightId);
        String seatId = findAvailableSeat(seatMap);

        BookingRequest request = new BookingRequest();
        request.setJourneyType(TestData.JOURNEY_TYPE);
        request.setInventoryId(flightId);
        request.setSeatIds(Collections.singletonList(seatId));
        request.setRefundable(TestData.REFUNDABLE);
        request.setHoldTtlSec(TestData.HOLD_TIME);

        BookingService bookingService = new BookingService();
        BookingResponse booking = bookingService.createBooking(request, token);
        Assertions.assertEquals("HELD", booking.getState());

        booking = bookingService.payBooking(booking.getId(), token);
        Assertions.assertEquals("PAYMENT_PENDING", booking.getState());

        booking = bookingService.confirmBooking(booking.getId(), token);
        Assertions.assertEquals("CONFIRMED", booking.getState());
        Assertions.assertNotNull(booking.getPnr());

        System.out.println("Booking ID: " + booking.getId());
        System.out.println("PNR: " + booking.getPnr());
    }

    private String findAvailableSeat(SeatMapResponse seatMap) {
        for (SeatMapResponse.Row row : seatMap.getRows()) {
            for (SeatMapResponse.Seat seat : row.getSeats()) {
                if (!seat.isOccupied()) {
                    return seat.getSeatId();
                }
            }
        }
        return null;
    }
}