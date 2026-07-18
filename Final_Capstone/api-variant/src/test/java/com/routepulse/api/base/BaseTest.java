package com.ust.sdet.api.base;

import com.ust.sdet.api.client.ApiClient;
import com.ust.sdet.api.config.ConfigManager;
import com.ust.sdet.api.specs.RequestSpecFactory;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseTest {
    protected final ConfigManager configManager = ConfigManager.getInstance();
    protected final RequestSpecFactory requestSpecFactory = new RequestSpecFactory();
    protected final ApiClient apiClient = new ApiClient();

    protected String token;

    protected void login() {
        String loginPayload = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", configManager.getEmail(), configManager.getPassword());
        Response response = apiClient.post(
                "/api/auth/login",
                loginPayload,
                requestSpec()
        );

        response.then().statusCode(200);
        token = response.jsonPath().getString("token");
    }

    protected void resetNamespace() {
        if (token == null) {
            login();
        }

        Response response = apiClient.post(
                "/api/reset",
                null,
                authSpec()
        );

        response.then().statusCode(200);
    }

    protected RequestSpecification requestSpec() {
        return requestSpecFactory.build(configManager);
    }

    protected RequestSpecification authSpec() {
        if (token == null) {
            login();
        }
        return requestSpecFactory.buildWithAuth(configManager, token);
    }

    protected RequestSpecification authSpec(String token) {
        return requestSpecFactory.buildWithAuth(configManager, token);
    }
}
