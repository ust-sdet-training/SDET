package org.sdet.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sdet.API_Framework.builders.LoginBuilder;
import org.sdet.API_Framework.clients.AuthClient;
import org.sdet.API_Framework.model.request.LoginRequest;
import org.sdet.API_Framework.model.response.LoginResponse;
import org.sdet.API_Framework.utils.TokenManager;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTests extends BaseTest {

    private final AuthClient authClient = new AuthClient();

    @Test
    @DisplayName("Verify user can login successfully")
    public void shouldLoginSuccessfully() {

        LoginRequest request = LoginBuilder.validLogin();

        Response response = authClient.login(request);

        assertEquals(200, response.getStatusCode());

        LoginResponse loginResponse =
                response.as(LoginResponse.class);

        assertNotNull(loginResponse.getToken());

        assertFalse(loginResponse.getToken().isBlank());

        TokenManager.setToken(loginResponse.getToken());

    }

    @Test
    @DisplayName("Verify login fails with invalid password")
    public void shouldFailForInvalidPassword() {

        LoginRequest request =
                LoginBuilder.invalidPassword();

        Response response =
                authClient.login(request);

        assertEquals(401,
                response.getStatusCode());

    }

    @Test
    @DisplayName("Verify login fails for empty credentials")
    public void shouldFailForEmptyCredentials() {

        LoginRequest request =
                LoginBuilder.emptyCredentials();

        Response response =
                authClient.login(request);

        assertTrue(response.getStatusCode() == 400
                || response.getStatusCode() == 401);

    }

}