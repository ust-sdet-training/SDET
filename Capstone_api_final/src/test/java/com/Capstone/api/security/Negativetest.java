package com.Capstone.api.security;

import com.Capstone.api.client.AuthClient;
import com.Capstone.api.client.BookingApiClient;
import com.Capstone.api.client.BusApiClient;
import com.Capstone.api.config.Config;
import com.Capstone.api.model.modelData.BusSeatMap;
import com.Capstone.api.model.modelData.BusSearchResponse;
import com.Capstone.api.model.modelData.LoginResponse;

import com.Capstone.api.model.modelData.Booking;
import com.Capstone.api.support.SpecFactory.utils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;



@Tag("security")
public class Negativetest {
    private final AuthClient authClient = new AuthClient();
    private final BusApiClient busApiClient = new BusApiClient();
    private final BookingApiClient bookingApiClient = new BookingApiClient();

    @Test
    void shouldRejectCancelOfAnotherEmployeesBooking() {
        LoginResponse me = authClient.login(Config.EMAIL, Config.PASSWORD);
        String travelDate = utils.travelDate();
        BusSearchResponse buses = busApiClient.search(me.token, Config.ORIGIN, Config.DESTINATION, travelDate);
        String busId = buses.buses.get(0).id;
        BusSeatMap seatMap = busApiClient.seatMap(me.token, busId);
        String seatId = availableSeat(seatMap);
        Booking myBooking = bookingApiClient.holdBusSeat(me.token, busId, seatId);
        LoginResponse otherUser = authClient.login(Config.VIEWER_EMAIL, Config.VIEWER_PASSWORD);
        bookingApiClient.assertCannotCancel(otherUser.token, myBooking.id);
    }
    @Test
    void shouldRejectTamperedToken() {
        LoginResponse me = authClient.login(Config.EMAIL, Config.PASSWORD);
        String tampered = me.token.substring(0, me.token.length() - 5) + "abcde";
        bookingApiClient.assertUnauthorized(tampered);
    }


    private String availableSeat(BusSeatMap seatMap) {
        return seatMap.decks.values().stream().flatMap(List::stream)
                .filter(s -> "available".equalsIgnoreCase(s.state))
                .findFirst().orElseThrow().seatId;
    }
    }

