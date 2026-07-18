package com.routepulse.api.base;

import com.routepulse.api.client.HttpGateway;
import com.routepulse.api.config.TravelConfigManager;
import com.routepulse.api.specs.RequestBlueprintFactory;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class JourneyBaseTest {
    protected final TravelConfigManager configManager = TravelConfigManager.getInstance();
    protected final RequestBlueprintFactory requestSpecFactory = new RequestBlueprintFactory();
    protected final HttpGateway apiClient = new HttpGateway();

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
