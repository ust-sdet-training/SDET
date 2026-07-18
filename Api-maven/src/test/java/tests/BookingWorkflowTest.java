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

import java.util.List;

public class BookingWorkflowTest extends BaseTest {

    @Test
    @DisplayName("Verify complete booking workflow")
    void bookingWorkflow() {
        String token = new AuthService()
                .login(TestData.EMAIL, TestData.PASSWORD)
                .token();
        FlightResponse flights = new FlightService().searchFlights(
                TestData.FROM,
                TestData.TO,
                TestData.DATE,
                TestData.TRAVEL_CLASS
        );
        String flightId = flights.flights().getFirst().id();
        String seatId = new SeatService().getSeatMap(flightId).firstAvailableSeatId();
        Assertions.assertNotNull(seatId, "At least one seat should be available");
        BookingRequest request = new BookingRequest(
                TestData.JOURNEY_TYPE,
                flightId,
                List.of(seatId),
                TestData.REFUNDABLE,
                TestData.HOLD_TIME
        );
        BookingService bookingService = new BookingService();
        BookingResponse booking = bookingService.createBooking(request, token);
        Assertions.assertEquals("HELD", booking.state());
        booking = bookingService.payBooking(booking.id(), token);
        Assertions.assertEquals("PAYMENT_PENDING", booking.state());
        booking = bookingService.confirmBooking(booking.id(), token);
        Assertions.assertEquals("CONFIRMED", booking.state());
        Assertions.assertNotNull(booking.pnr());
        System.out.println("Booking ID: " + booking.id());
        System.out.println("PNR: " + booking.pnr());
    }
}
