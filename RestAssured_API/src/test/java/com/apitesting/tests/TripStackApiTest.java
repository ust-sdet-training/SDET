package com.apitesting.tests;

import com.apitesting.config.ApiConfig;

import com.apitesting.data.model.ApiModels.Booking;
import com.apitesting.data.model.ApiModels.BusSearchResponse;
import com.apitesting.data.model.ApiModels.BusSeatMap;
import com.apitesting.data.model.ApiModels.LoginResponse;
import com.apitesting.support.BaseApiTest;
import com.apitesting.support.DateUtils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class TripStackApiTest extends BaseApiTest {
    @Test
    void shouldBookBusJourneyAndVerifyNamespaceAndPnr() {
        LoginResponse user = authApi.login(ApiConfig.EMAIL, ApiConfig.PASSWORD);
        namespaceApi.reset(user.token);

        BusSearchResponse buses = busApi.search(user.token, ApiConfig.ORIGIN, ApiConfig.DESTINATION, DateUtils.travelDate());
        Assertions.assertThat(buses.buses).isNotEmpty();
        BusSearchResponse.Bus selectedBus = buses.buses.get(0);

        String seatId = availableSeats(busApi.seatMap(user.token, selectedBus.id)).get(0).seatId;
        Booking held = bookingApi.holdBusSeat(user.token, selectedBus.id, seatId);
        Assertions.assertThat(held.state).isEqualTo("HELD");
        Assertions.assertThat(held.empId).isEqualTo(user.empId);
        Assertions.assertThat(held.pnr).isNull();

        Booking paymentPending = bookingApi.pay(user.token, held.id);
        Assertions.assertThat(paymentPending.state).isEqualTo("PAYMENT_PENDING");

        Booking confirmed = bookingApi.confirm(user.token, held.id);
        Assertions.assertThat(confirmed.state).isEqualTo("CONFIRMED");
        Assertions.assertThat(confirmed.pnr).matches("TS-" + user.empId + "-\\d{4}");

        Booking[] bookings = bookingApi.list(user.token);
        Assertions.assertThat(bookings).extracting(booking -> booking.pnr).contains(confirmed.pnr);
        Assertions.assertThat(bookings).allMatch(booking -> user.empId.equals(booking.empId));
        Assertions.assertThat(bookingApi.getByPnr(user.token, confirmed.pnr).pnr).isEqualTo(confirmed.pnr);

      
    }

    @Test
    void shouldRejectAnExpiredToken() throws InterruptedException {
        LoginResponse user = authApi.login(ApiConfig.EMAIL, ApiConfig.PASSWORD, 2);
        Thread.sleep(3200);
        bookingApi.assertUnauthorized(user.token);
    }

    private List<BusSeatMap.DeckSeat> availableSeats(BusSeatMap seatMap) {
        List<BusSeatMap.DeckSeat> seats = seatMap.decks.values().stream().flatMap(List::stream)
                .filter(seat -> "available".equalsIgnoreCase(seat.state)).toList();
        Assertions.assertThat(seats).isNotEmpty();
        return seats;
    }
}
