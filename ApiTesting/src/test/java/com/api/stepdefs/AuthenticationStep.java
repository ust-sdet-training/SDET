package com.api.stepdefs;

import com.api.clients.AuthClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class AuthenticationStep {

    private final AuthClient authClient;

    public AuthenticationStep() {
        authClient = new AuthClient();
    }

    @Step("Authenticate as Dave")
    public Response makeTheUserAuthentication() {
        return authClient.loginAsDave();
    }
}