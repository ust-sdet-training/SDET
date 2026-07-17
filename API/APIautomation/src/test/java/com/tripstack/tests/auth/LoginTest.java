package com.tripstack.tests.auth;

import com.tripstack.client.AuthClient;
import com.tripstack.config.ConfigManager;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class LoginTest {

    @Test
    public void verifyLogin() {

        AuthClient authClient =
                new AuthClient();

        Response response =
                authClient.login(
                        ConfigManager.EMAIL,
                        ConfigManager.PASSWORD
                );

        response.prettyPrint();
    }
}