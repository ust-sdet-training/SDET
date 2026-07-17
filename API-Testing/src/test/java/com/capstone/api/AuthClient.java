package com.capstone.api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class AuthClient {
    private final RequestSpecification request;

    public AuthClient(RequestSpecification request) {
        this.request = request;
    }

    public Response login(String email, String password) {
        return given(request)
                .body(Map.of("email", email, "password", password))
                .when()
                .post("/auth/login");
    }
}

