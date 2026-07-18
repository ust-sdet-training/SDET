//package com.week7.finalgate.API.workflow;
//
//import com.week7.finalgate.API.models.BookingResponse;
//import com.week7.finalgate.API.models.Flight;
//import com.week7.finalgate.API.models.LoginResponse;
//import com.week7.finalgate.API.models.Seat;
//
//import com.week7.finalgate.API.service.AuthService;
//import com.week7.finalgate.API.service.BookingService;
//import com.week7.finalgate.API.service.FlightService;
//import com.week7.finalgate.API.service.SeatService;
//
//import java.util.List;
//
//public class BookingWorkflow {
//
//    private final AuthService authService = new AuthService();
//    private final FlightService flightService = new FlightService();
//    private final SeatService seatService = new SeatService();
//    private final BookingService bookingService = new BookingService();
//
//    public BookingResponse completeBooking() {
//
//        LoginResponse login =
//                authService.login(
//                        "uma@tripstack.test",
//                        "Password@123"
//                );
//
//        // Your AuthService may already store the token internally.
//        // If not, pass login.getToken() into the remaining service calls.
//
//        List<Flight> flights =
//                flightService.searchFlights(
//                        "MAA",
//                        "BLR",
//                        6
//                );
//
//        Flight selectedFlight = flights.get(0);
//
//        List<Seat> seats =
//                seatService.getSeatMap(
//                        selectedFlight.getInventoryId()
//                );
//
//        Seat selectedSeat =
//                seats.stream()
//                        .filter(Seat::isAvailable)
//                        .findFirst()
//                        .orElseThrow();
//
//        bookingService.holdBooking(
//                selectedFlight.getInventoryId(),
//                List.of(selectedSeat.getSeatId())
//        );
//
//        bookingService.pay();
//
//        return bookingService.confirm();
//    }
//
//}