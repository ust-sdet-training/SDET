package org.sdet.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sdet.API_Framework.builders.LoginBuilder;
import org.sdet.API_Framework.clients.AuthClient;
import org.sdet.API_Framework.clients.ResetClient;
import org.sdet.API_Framework.model.response.LoginResponse;
import org.sdet.API_Framework.utils.TokenManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResetTest extends BaseTest {

    private final AuthClient authClient = new AuthClient();
    private final ResetClient resetClient = new ResetClient();

    @BeforeEach
    void login() {

        Response loginResponse =
                authClient.login(LoginBuilder.validLogin());

        TokenManager.setToken(
                loginResponse.as(LoginResponse.class).getToken()
        );

    }

    @Test
    @DisplayName("Verify application data can be reset")
    void shouldResetApplicationData() {

        Response response = resetClient.resetData();

        assertEquals(200, response.getStatusCode());

    }

}