package com.apitesting.api;

import com.apitesting.data.model.ApiModels.LoginResponse;
import com.apitesting.support.specifications.RequestSpecifications;
import com.apitesting.support.specifications.ResponseSpecifications;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class AuthApi {
    public LoginResponse login(String email, String password) {
        return login(email, password, null);
    }

    public LoginResponse login(String email, String password, Integer ttlSeconds) {
        Map<String, Object> request = new HashMap<>();
        request.put("email", email);
        request.put("password", password);
        if (ttlSeconds != null) request.put("ttlSeconds", ttlSeconds);

        return given().spec(RequestSpecifications.defaultRequest()).body(request)
                .when().post("/auth/login")
                .then().spec(ResponseSpecifications.ok())
                .extract().as(LoginResponse.class);
    }
}
