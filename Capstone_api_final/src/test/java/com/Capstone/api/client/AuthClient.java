package com.Capstone.api.client;
import com.Capstone.api.model.modelData;

import java.util.HashMap;
import java.util.Map;
import com.Capstone.api.model.modelData.LoginResponse;
import com.Capstone.api.support.SpecFactory.reqSpec;
import com.Capstone.api.support.SpecFactory.respSpec;
import io.restassured.specification.RequestSpecification;

import static com.Capstone.api.support.SpecFactory.reqSpec.Defrequest;
import static io.restassured.RestAssured.given;

public class AuthClient {

    public static modelData.LoginResponse login(String email, String password) {
        Map<String, Object> request = new HashMap<>();
        request.put("email", email);
        request.put("password", password);
        return given()
                .spec(Defrequest())
                .body(request)
                .post("/auth/login")
                .then()
                .spec(respSpec.okSuccess())
                .extract()
                .as(LoginResponse.class);
    }
}