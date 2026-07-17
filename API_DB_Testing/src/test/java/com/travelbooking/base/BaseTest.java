package com.travelbooking.base;

import com.travelbooking.clients.AuthClient;
import com.travelbooking.clients.BookingClient;
import com.travelbooking.clients.PaymentClient;
import com.travelbooking.clients.SearchClient;
import com.travelbooking.config.Secrets;
import com.travelbooking.models.request.LoginRequest;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    protected AuthClient authClient;
    protected SearchClient searchClient;
    protected BookingClient bookingClient;
    protected PaymentClient paymentClient;

    protected String token;

    @BeforeEach
    void setUp() {

        authClient = new AuthClient();
        searchClient = new SearchClient();
        bookingClient = new BookingClient();
        paymentClient = new PaymentClient();

        LoginRequest loginRequest = new LoginRequest(
                Secrets.EMAIL,
                Secrets.PASSWORD
        );

        token = authClient.getToken(loginRequest);
    }
}