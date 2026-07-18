package com.week7.finalgate.tests;

import com.week7.finalgate.API.Factory.RestClient;
import com.week7.finalgate.API.models.LoginResponse;
import com.week7.finalgate.API.service.AuthService;
import com.week7.finalgate.API.support.ApiContext;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginApiTest {

    @BeforeAll
    static void setup() {
        RestClient.initialize();
    }

    @Test
    void loginShouldReturnBearerToken() {

        ApiContext context = new ApiContext();

        AuthService auth = new AuthService(context);

        LoginResponse response = auth.login();

        assertNotNull(response.getToken());

        assertEquals("1021", response.getEmpId());

        assertEquals("traveller", response.getRole());

        assertNotNull(context.getToken());

    }

}