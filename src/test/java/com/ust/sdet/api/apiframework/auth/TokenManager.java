package com.ust.sdet.api.apiframework.auth;

import static io.restassured.RestAssured.given;

import com.ust.sdet.api.apiframework.config.ConfigReader;

public class TokenManager {

    private static final String BASE_URL =
            ConfigReader.get("BASE_URL", "http://localhost:4000");

    public static String getToken(
            String clientId,
            String clientSecret) {

                try {
                        return given()
                                        .baseUri(BASE_URL)
                                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                                        .auth()
                                        .preemptive()
                                        .basic(clientId, clientSecret)
                                        .formParam("grant_type", "client_credentials")

                                        .when()
                                        .post("/api/oauth/token")

                                        .then()
                                        .statusCode(200)
                                        .extract()
                                        .path("access_token");
                } catch (Throwable e) {
                        return null;
                }
    }
}