package com.tripstack.client;

import com.tripstack.config.ConfigManager;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthClient {

    @Step("POST /auth/login")
    public Response loginRaw(String email, String password) {
        String body = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password);
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .contentType("application/json")
                .body(body)
                .when()
                .post("/auth/login");
    }

    @Step("Log in and extract token")
    public String login(String email, String password) {
        Response response = loginRaw(email, password);
        response.then().statusCode(200);
        return response.jsonPath().getString("token");
    }

    @Step("GET /auth/me")
    public Response me(String token) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/auth/me");
    }

    @Step("GET /auth/admin-ping")
    public Response adminPing(String token) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/auth/admin-ping");
    }
}