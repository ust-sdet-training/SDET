package com.api.stepdefs;

import com.api.clients.ResetClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class ResetStep {

    private final ResetClient resetClient;

    public ResetStep() {
        resetClient = new ResetClient();
    }

    @Step("Reset current employee namespace")
    public Response resetNamespace(String token) {
        return resetClient.resetNamespace(token);
    }

    @Step("Reset namespace for employee {0}")
    public Response resetAnotherNamespace(String token, String empId) {
        return resetClient.resetAnotherNamespace(token, empId);
    }
}