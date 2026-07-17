package com.apitesting.client;

import com.apitesting.support.builders.ApiSpecBuilders;
import io.restassured.response.Response;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class AuthClient {
        public Response login(String email, String password) {
            return given()
                    .spec(ApiSpecBuilders.requestSpec())
                    .body(Map.of("email", email, "password", password))
                    .when()
                    .post("/auth/login");
        }
    public Response getMe(String token) {
        return given()
                .spec(ApiSpecBuilders.authSpec(token))
                .when()
                .get("/auth/me");
    }
}