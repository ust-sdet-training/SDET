package com.week7.finalgate.DB.tests;

import com.week7.finalgate.API.models.BookingResponse;
import com.week7.finalgate.API.support.ApiContext;
import com.week7.finalgate.API.service.AuthService;
import com.week7.finalgate.API.service.BookingService;
import com.week7.finalgate.API.service.FlightService;
import com.week7.finalgate.API.service.SeatService;
import com.week7.finalgate.DB.assertions.BookingAssertions;
import com.week7.finalgate.DB.assertions.SeatAssertions;
import com.week7.finalgate.DB.model.BookingRecord;
import com.week7.finalgate.DB.repository.BookingRepository;
import com.week7.finalgate.DB.repository.BookingSeatRepository;
import com.week7.finalgate.DB.support.DatabasePersistenceService;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class DatabaseValidationTest {

    @Test
    void verifyBookingPersistedCorrectly() {

        BookingResponse confirmedBooking = executeBookingFlow();

        DatabasePersistenceService persistence =
                new DatabasePersistenceService();

        persistence.persist(confirmedBooking);

        BookingRepository bookingRepository =
                new BookingRepository();

        BookingSeatRepository seatRepository =
                new BookingSeatRepository();

        BookingRecord dbBooking =
                bookingRepository
                        .findByPNR(confirmedBooking.getPnr())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Booking not found in database."
                                )
                        );

        List<String> dbSeats =
                seatRepository.findSeatsByBookingUuid(
                        confirmedBooking.getId()
                );

        BookingAssertions.verifyBooking(
                confirmedBooking,
                dbBooking
        );

        SeatAssertions.verifySeats(
                confirmedBooking.getSeatIds(),
                dbSeats
        );

    }

    private BookingResponse executeBookingFlow() {

        ApiContext context = new ApiContext();

        AuthService authService =
                new AuthService(context);

        FlightService flightService =
                new FlightService(context);

        SeatService seatService =
                new SeatService(context);

        BookingService bookingService =
                new BookingService(context);

        // Login
        authService.login();

        // Search Flight
        flightService.searchFlight(
                "MAA",
                "BLR",
                LocalDate.now()
                        .plusDays(6)
                        .toString()
        );

        // Select Seat
        seatService.selectFirstAvailableSeat();

        // Hold Seat
        bookingService.holdSeat();

        // Pay
        bookingService.pay();

        // Confirm Booking
        return bookingService.confirm();
    }

}