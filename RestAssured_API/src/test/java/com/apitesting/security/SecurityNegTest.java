package com.apitesting.security;

import com.apitesting.api.AuthApi;
import com.apitesting.api.BookingApi;
import com.apitesting.api.BusApi;
import com.apitesting.config.ApiConfig;
import com.apitesting.data.model.ApiModels.Booking;
import com.apitesting.data.model.ApiModels.BusSearchResponse;
import com.apitesting.data.model.ApiModels.BusSeatMap;
import com.apitesting.data.model.ApiModels.LoginResponse;
import com.apitesting.support.DateUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

@Tag("security")
class SecurityNegTest {

    private final AuthApi authApi = new AuthApi();
    private final BusApi busApi = new BusApi();
    private final BookingApi bookingApi = new BookingApi();

    @Test
    void shouldRejectCancelOfAnotherEmployeesBooking() {
        LoginResponse me = authApi.login(ApiConfig.EMAIL, ApiConfig.PASSWORD);
        String travelDate = DateUtils.travelDate();
        BusSearchResponse buses = busApi.search(me.token, ApiConfig.ORIGIN, ApiConfig.DESTINATION, travelDate);
        String busId = buses.buses.get(0).id;
        BusSeatMap seatMap = busApi.seatMap(me.token, busId);
        String seatId = availableSeat(seatMap);
        Booking myBooking = bookingApi.holdBusSeat(me.token, busId, seatId);

        LoginResponse otherUser = authApi.login(ApiConfig.VIEWER_EMAIL, ApiConfig.VIEWER_PASSWORD);

        bookingApi.assertCannotCancel(otherUser.token, myBooking.id);
    }

    @Test
    void shouldRejectTamperedToken() {
        LoginResponse me = authApi.login(ApiConfig.EMAIL, ApiConfig.PASSWORD);
        String tampered = me.token.substring(0, me.token.length() - 5) + "abcde";
        bookingApi.assertUnauthorized(tampered);
    }

    private String availableSeat(BusSeatMap seatMap) {
        return seatMap.decks.values().stream().flatMap(List::stream)
                .filter(s -> "available".equalsIgnoreCase(s.state))
                .findFirst().orElseThrow().seatId;
    }
}
