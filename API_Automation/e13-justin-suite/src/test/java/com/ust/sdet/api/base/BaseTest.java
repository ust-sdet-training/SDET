package com.ust.sdet.api.base;

import com.ust.sdet.api.client.ApiClient;
import com.ust.sdet.api.config.ConfigManager;
import com.ust.sdet.api.specs.RequestSpecFactory;
import com.ust.sdet.api.specs.ResponseSpecFactory;
import io.restassured.response.Response;

public class BaseTest {
    protected final ConfigManager configManager = ConfigManager.getInstance();
    protected final RequestSpecFactory requestSpecFactory = new RequestSpecFactory();
    protected final ResponseSpecFactory responseSpecFactory = new ResponseSpecFactory();
    protected final ApiClient apiClient = new ApiClient();

    protected String token;

    protected void login() {
        String loginPayload = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", configManager.getEmail(), configManager.getPassword());
        Response response = apiClient.post(
                "/api/auth/login",
                loginPayload,
                requestSpecFactory.build(configManager)
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
                requestSpecFactory.buildWithAuth(configManager, token)
        );

        response.then().statusCode(200);
    }
}
