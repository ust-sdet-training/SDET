package com.ust.capstone.api;

import com.ust.capstone.data.TestUsers;
import com.ust.capstone.data.secret.Secrets;
import io.restassured.response.Response;

import java.util.Map;

import static com.ust.capstone.api.ApiSpec.requestSpec;
import static io.restassured.RestAssured.given;

public class AuthClient {
    public static Response login(String email, String password) {
        var data = Map.of("email", email, "password", password);
        return given()
                .spec(requestSpec())
                .body(data)
                .when()
                .post("/auth/login");
    }

    public static String loginAsBob() {
        Response response = login(
                TestUsers.BOB_EMAIL,
                Secrets.get("TRIPSTACK_BOB_PASSWORD")
        );
        return response.path("token");
    }
}