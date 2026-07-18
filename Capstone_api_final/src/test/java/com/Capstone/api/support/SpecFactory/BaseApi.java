package com.Capstone.api.support.SpecFactory;

import com.Capstone.api.client.AuthClient;
import com.Capstone.api.client.BookingApiClient;
import com.Capstone.api.client.BusApiClient;
import com.Capstone.api.client.ResetApi;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseApi {
    protected static AuthClient authClient;
    protected static BusApiClient busApi;
    protected static BookingApiClient bookingApi;
    protected static ResetApi resetApi;
    @BeforeAll
    static void configureApi() {
        authClient = new AuthClient();
        busApi = new BusApiClient();
        bookingApi = new BookingApiClient();
        resetApi=new ResetApi();
    }
}
