package com.apitesting.support;

import com.apitesting.api.AuthApi;
import com.apitesting.api.BookingApi;
import com.apitesting.api.BusApi;
import com.apitesting.api.NamespaceApi;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;


public abstract class BaseApiTest {
    protected static AuthApi authApi;
    protected static BusApi busApi;
    protected static BookingApi bookingApi;
    protected static NamespaceApi namespaceApi;

    @BeforeAll
    static void configureApi() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        authApi = new AuthApi();
        busApi = new BusApi();
        bookingApi = new BookingApi();
        namespaceApi = new NamespaceApi();
    }
}
