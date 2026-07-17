package com.travel.clients;

import com.travel.models.request.LoginRequest;
import com.travel.models.response.LoginResponse;
import com.travel.specs.ResponseSpec;
import com.travel.utils.EnvReader;

public class AuthClient extends BaseAPIClient {

    public LoginResponse login() {
        LoginRequest request = new LoginRequest(EnvReader.get("USER_EMAIL"), EnvReader.get("USER_PASSWORD"));
        return post("/auth/login", request).then().spec(ResponseSpec.ok()).extract().as(LoginResponse.class);
    }

    public String token() {
        return login().token();
    }
}