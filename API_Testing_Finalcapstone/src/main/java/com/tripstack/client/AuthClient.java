package com.tripstack.client;

import com.tripstack.core.BaseApiClient;
import com.tripstack.endpoints.Endpoints;
import com.tripstack.model.LoginRequest;
import com.tripstack.model.LoginResponse;

import io.restassured.response.Response;

public class AuthClient extends BaseApiClient {

    public Response loginResponse(LoginRequest request) {
        return post(Endpoints.LOGIN, request);
    }

    public LoginResponse login(LoginRequest request) {
        return parseJson(loginResponse(request), LoginResponse.class);
    }

    public Response meResponse(String token) {
        return get(Endpoints.ME, token);
    }

    public LoginResponse me(String token) {
        return parseJson(meResponse(token), LoginResponse.class);
    }
}
