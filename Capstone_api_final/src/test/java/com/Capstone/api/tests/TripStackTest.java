package com.Capstone.api.tests;

import com.Capstone.api.client.AuthClient;
import com.Capstone.api.client.BookingApiClient;
import com.Capstone.api.client.BusApiClient;
import com.Capstone.api.client.ResetApi;

import com.Capstone.api.config.Config;
import com.Capstone.api.model.modelData.Booking;
import com.Capstone.api.model.modelData.BusSeatMap;
import com.Capstone.api.model.modelData.BusSearchResponse;
import com.Capstone.api.model.modelData.LoginResponse;
import com.Capstone.api.support.SpecFactory.BaseApi;
import com.Capstone.api.support.SpecFactory.utils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class TripStackTest extends BaseApi {
    @Test
    void E2EFlowtest() {
        LoginResponse user = authClient.login(Config.EMAIL, Config.PASSWORD);

        BusSearchResponse buses = busApi.search(user.token, Config.ORIGIN, Config.DESTINATION, utils.travelDate());
        BusSearchResponse.Bus selectedBus = buses.buses.get(0);

        String seatId = availableSeats(busApi.seatMap(user.token, selectedBus.id)).get(0).seatId;
        Booking in = bookingApi.holdBusSeat(user.token, selectedBus.id, seatId);
        Assertions.assertThat(in.empId).isEqualTo(user.empId);
        Assertions.assertThat(in.pnr).isNull();

        Booking paymentPending = bookingApi.payPending(user.token, in.id);
        Assertions.assertThat(paymentPending.state).isEqualTo("PAYMENT_PENDING");

        Booking confirmed = bookingApi.confirm(user.token, in.id);
        Assertions.assertThat(confirmed.state).isEqualTo("CONFIRMED");
        Assertions.assertThat(confirmed.pnr).matches("TS-" + user.empId + "-\\d{4}");

        Booking[] bookings = bookingApi.listBooking(user.token);
        Assertions.assertThat(bookings).extracting(booking -> booking.pnr).contains(confirmed.pnr);
        Assertions.assertThat(bookings).allMatch(booking -> user.empId.equals(booking.empId));

    }

    private List<BusSeatMap.DeckSeat> availableSeats(BusSeatMap seatMap) {
        List<BusSeatMap.DeckSeat> seats = seatMap.decks.values().stream().flatMap(List::stream)
                .filter(seat -> "available".equalsIgnoreCase(seat.state)).toList();
        Assertions.assertThat(seats).isNotEmpty();
        return seats;
    }
}