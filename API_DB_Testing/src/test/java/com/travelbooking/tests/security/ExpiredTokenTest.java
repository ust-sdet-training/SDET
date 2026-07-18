package com.travelbooking.tests.security;

import com.travelbooking.clients.AuthClient;
import com.travelbooking.models.request.LoginRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpiredTokenTest {

    private final AuthClient authClient = new AuthClient();

    @Test
    void shouldReturn401ForExpiredToken() {

        LoginRequest request = new LoginRequest();
        request.setEmail("zara@tripstack.test");
        request.setPassword("Password@123");

        String validToken = authClient.getToken(request);

        String invalidToken = validToken + "expired";

        Response response = authClient.getCurrentUser(invalidToken);

        assertEquals(401, response.getStatusCode());
    }
}