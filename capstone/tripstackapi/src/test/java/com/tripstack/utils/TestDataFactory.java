package com.tripstack.utils;

import com.tripstack.config.ConfigReader;
import com.tripstack.model.request.BookingRequest;
import com.tripstack.model.request.FlightSearchRequest;
import com.tripstack.model.request.LoginRequest;

import java.util.List;

public final class TestDataFactory {

    private TestDataFactory() {
    }

    public static LoginRequest validLogin() {

        return new LoginRequest(
                ConfigReader.getEmail(),
                ConfigReader.getPassword()
        );
    }

    public static LoginRequest invalidPassword() {

        return new LoginRequest(
                ConfigReader.getEmail(),
                "InvalidPassword"
        );
    }

    public static LoginRequest invalidEmail() {

        return new LoginRequest(
                "invalid@tripstack.test",
                ConfigReader.getPassword()
        );
    }

    public static FlightSearchRequest flightSearch() {

        return new FlightSearchRequest(
                ConfigReader.getFrom(),
                ConfigReader.getTo(),
                ConfigReader.getTravelDate(),
                ConfigReader.getPassengers(),
                ConfigReader.getTravelClass()
        );
    }

    public static BookingRequest bookingRequest(String inventoryId,
                                                String seatId) {

        return new BookingRequest(
                "flight",
                inventoryId,
                List.of(seatId),
                true,
                null
        );
    }

}