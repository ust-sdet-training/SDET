package com.api.stepdefs;

import com.api.clients.GetCurrentIdentityClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class GetCurrentIdentityStep {

    GetCurrentIdentityClient currentIdentityClient;

    public GetCurrentIdentityStep() {
        currentIdentityClient = new GetCurrentIdentityClient();
    }

    @Step
    public Response getCurrentIdentity(String token) {
        return currentIdentityClient.getCurrentIdentity(token);
    }
}