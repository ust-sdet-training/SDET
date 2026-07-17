package com.tripstack.client;

import com.tripstack.config.ConfigManager;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthClient {

    private final String BASE_URL =
            ConfigManager.get("base.url");

    public Response login(
            String email,
            String password
    ) {

        return given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .body(
                        """
                        {
                          "email":"%s",
                          "password":"%s"
                        }
                        """
                                .formatted(
                                        email,
                                        password
                                )
                )
                .log().all()
                .when()
                .post("/api/auth/login")
                .then()
                .log().all()
                .extract()
                .response();
    }
}