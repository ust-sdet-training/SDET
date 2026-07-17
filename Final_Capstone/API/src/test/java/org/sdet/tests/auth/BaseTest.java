package org.sdet.base;

import org.junit.jupiter.api.BeforeEach;
import org.sdet.clients.AuthClient;
import org.sdet.clients.BookingClient;
import org.sdet.clients.SearchClient;

public class BaseTest {

    protected AuthClient authClient;
    protected SearchClient searchClient;
    protected BookingClient bookingClient;

    @BeforeEach
    public void setup() {

        authClient = new AuthClient();
        searchClient = new SearchClient();
        bookingClient = new BookingClient();

    }

}