package com.travelbooking.clients;

import com.travelbooking.models.request.LoginRequest;
import io.restassured.response.Response;

public class AuthClient extends BaseClient {

    private static final String LOGIN = "/api/auth/login";
    private static final String ME = "/api/auth/me";

    public Response login(LoginRequest request) {
        return post(LOGIN, request);
    }


    public Response getCurrentUser(String token) {
        return get(ME, token);
    }


    public String getToken(LoginRequest request) {
        return login(request)
                .jsonPath()
                .getString("token");
    }

    public String getEmployeeId(String token) {
        return getCurrentUser(token)
                .jsonPath()
                .getString("empId");
    }


    public String getRole(String token) {
        return getCurrentUser(token)
                .jsonPath()
                .getString("role");
    }


    public String getDisplayName(String token) {
        return getCurrentUser(token)
                .jsonPath()
                .getString("displayName");
    }
}