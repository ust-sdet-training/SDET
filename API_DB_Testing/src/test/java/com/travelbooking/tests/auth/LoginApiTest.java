package com.travelbooking.tests.auth;

import com.travelbooking.base.BaseTest;
import com.travelbooking.models.request.LoginRequest;
import com.travelbooking.models.response.LoginResponse;
import com.travelbooking.config.Secrets;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginApiTest extends BaseTest {

    @Test
    void validLogin() {

        LoginRequest request = new LoginRequest(
                Secrets.EMAIL,
                Secrets.PASSWORD
        );

        Response response = authClient.login(request);

        assertEquals(200, response.getStatusCode());

        LoginResponse loginResponse = response.as(LoginResponse.class);

        assertNotNull(loginResponse.getToken());
        assertFalse(loginResponse.getToken().isBlank());
    }

    @Test
    void invalidLogin() {

        LoginRequest request = new LoginRequest(
                "invalid@test.com",
                "WrongPassword"
        );

        Response response = authClient.login(request);

        assertEquals(401, response.getStatusCode());
    }
}