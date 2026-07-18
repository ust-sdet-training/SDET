package com.tripstack.api;

import com.tripstack.base.BaseTest;
import com.tripstack.base.ResponseSpecificationBuilder;
import com.tripstack.config.ConfigReader;
import com.tripstack.model.request.LoginRequest;
import com.tripstack.services.AuthService;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class NegativeLoginTest extends BaseTest {

    private final AuthService authService = new AuthService();

    @Test
    void verifyInvalidPassword() {

        LoginRequest request = new LoginRequest(
                ConfigReader.getEmail(),
                "WrongPassword"
        );

        Response response = authService.login(request);

        response.then()
                .spec(ResponseSpecificationBuilder.unauthorized401());
    }

    @Test
    void verifyInvalidEmail() {

        LoginRequest request = new LoginRequest(
                "invalid@tripstack.test",
                ConfigReader.getPassword()
        );

        Response response = authService.login(request);

        response.then()
                .spec(ResponseSpecificationBuilder.unauthorized401());
    }

    @Test
    void verifyInvalidEmailAndPassword() {

        LoginRequest request = new LoginRequest(
                "invalid@tripstack.test",
                "WrongPassword"
        );

        Response response = authService.login(request);

        response.then()
                .spec(ResponseSpecificationBuilder.unauthorized401());
    }

    @Test
    void verifyEmptyEmail() {

        LoginRequest request = new LoginRequest(
                "",
                ConfigReader.getPassword()
        );

        Response response = authService.login(request);

        response.then()
                .spec(ResponseSpecificationBuilder.badRequest400());
    }

    @Test
    void verifyEmptyPassword() {

        LoginRequest request = new LoginRequest(
                ConfigReader.getEmail(),
                ""
        );

        Response response = authService.login(request);

        response.then()
                .spec(ResponseSpecificationBuilder.badRequest400());

    }

    @Test
    void verifyEmptyCredentials() {

        LoginRequest request = new LoginRequest(
                "",
                ""
        );

        Response response = authService.login(request);

        response.then()
                .spec(ResponseSpecificationBuilder.badRequest400());

    }

    @Test
    void verifyNullEmail() {

        LoginRequest request = new LoginRequest(
                null,
                ConfigReader.getPassword()
        );

        Response response = authService.login(request);

        response.then()
                .spec(ResponseSpecificationBuilder.badRequest400());
    }

    @Test
    void verifyNullPassword() {

        LoginRequest request = new LoginRequest(
                ConfigReader.getEmail(),
                null
        );

        Response response = authService.login(request);

        response.then()
                .spec(ResponseSpecificationBuilder.badRequest400());
    }

    @Test
    void verifyNullCredentials() {

        LoginRequest request = new LoginRequest(
                null,
                null
        );

        Response response = authService.login(request);

        response.then()
                .spec(ResponseSpecificationBuilder.badRequest400());
    }

}