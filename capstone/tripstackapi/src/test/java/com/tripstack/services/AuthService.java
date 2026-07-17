package com.tripstack.services;

import com.tripstack.base.RequestSpecificationBuilder;
import com.tripstack.config.ConfigReader;

import com.tripstack.model.request.LoginRequest;
import com.tripstack.model.response.LoginResponse;
import com.tripstack.constants.ApiEndpoints;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthService {

    public Response login(LoginRequest request) {

        return given()
                .spec(RequestSpecificationBuilder.requestSpecification())
                .body(request)
                .when()
                .post(ApiEndpoints.LOGIN);
    }

    public LoginResponse login() {

        LoginRequest request = new LoginRequest(
                ConfigReader.getEmail(),
                ConfigReader.getPassword()
        );

        return login(request)
                .then()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);
    }

    public String getToken() {
        return login().getToken();
    }

    public String getEmployeeId() {
        return login().getEmpId();
    }

}