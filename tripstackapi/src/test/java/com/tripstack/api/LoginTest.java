package com.tripstack.api;

import com.tripstack.base.BaseTest;
import com.tripstack.base.ResponseSpecificationBuilder;
import com.tripstack.model.request.LoginRequest;
import com.tripstack.model.response.LoginResponse;
import com.tripstack.services.AuthService;
import com.tripstack.utils.TestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest {

    private final AuthService authService = new AuthService();

    @Test
    @DisplayName("Verify successful login")
    void verifySuccessfulLogin() {
        LoginRequest request = TestDataFactory.validLogin();

        LoginResponse response = authService.login();
        assertNotNull(response);
        assertNotNull(response.getToken());
        assertNotNull(response.getEmpId());
        assertNotNull(response.getRole());
        assertNotNull(response.getDisplayName());

    }

    @Test
    @DisplayName("Verify invalid password login")
    void verifyInvalidPasswordLogin() {

        LoginRequest request = TestDataFactory.invalidPassword();

        authService.login(request)
                .then()
                .spec(ResponseSpecificationBuilder.unauthorized401());

    }

    @Test
    @DisplayName("Verify invalid email login")
    void verifyInvalidEmailLogin() {

        LoginRequest request = TestDataFactory.invalidEmail();

        authService.login(request)
                .then()
                .spec(ResponseSpecificationBuilder.unauthorized401());

    }

}