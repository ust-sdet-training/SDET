package com.tripstack.support;

import org.junit.jupiter.api.BeforeAll;

import com.tripstack.client.AuthClient;
import com.tripstack.client.BookingClient;
import com.tripstack.client.FlightClient;
import com.tripstack.model.LoginRequest;
import com.tripstack.model.LoginResponse;

public class BaseTest {
    protected static AuthClient authClient;
    protected static FlightClient flightClient;
    protected static BookingClient bookingClient;
    protected static String authToken;

    @BeforeAll
    static void setup() {
        authClient = new AuthClient();
        flightClient = new FlightClient();
        bookingClient = new BookingClient();

        LoginResponse loginResponse = authClient.login(new LoginRequest("grace@tripstack.test", "Password@123"));
        authToken = loginResponse.getToken();
    }
}
