package com.tripstack.client;

import com.tripstack.config.ConfigManager;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OpsClient {

    @Step("POST /reset (clear my namespace)")
    public Response resetNamespace(String token) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body("{}")
                .when()
                .post("/reset");
    }
}