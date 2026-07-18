package com.week7.finalgate.API.service;

import com.week7.finalgate.API.Factory.RequestFactory;
import com.week7.finalgate.API.config.ApiConfig;
import com.week7.finalgate.API.config.Endpoints;
import com.week7.finalgate.API.models.LoginRequest;
import com.week7.finalgate.API.models.LoginResponse;
import com.week7.finalgate.API.support.ApiContext;

import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthService {

    private final ApiContext context;

    public AuthService(ApiContext context) {
        this.context = context;
    }

    public LoginResponse login() {

        LoginRequest request = new LoginRequest(
                ApiConfig.EMAIL,
                ApiConfig.PASSWORD
        );

        Response response =
                RequestFactory.publicRequest()
                        .body(request)
                        .post(Endpoints.LOGIN);

        assertEquals(200, response.statusCode());

        LoginResponse loginResponse =
                response.as(LoginResponse.class);

        context.setToken(loginResponse.getToken());

        return loginResponse;
    }

}