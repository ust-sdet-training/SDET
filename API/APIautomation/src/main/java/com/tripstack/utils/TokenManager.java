package com.tripstack.utils;

import com.tripstack.client.AuthClient;
import com.tripstack.config.ConfigManager;
import io.restassured.response.Response;

public class TokenManager {

    private static String token;

    public static String getToken() {

        if (token == null) {

            AuthClient authClient =
                    new AuthClient();

            Response response =
                    authClient.login(
                            ConfigManager.EMAIL,
                            ConfigManager.PASSWORD
                    );

            token =
                    response.jsonPath()
                            .getString("token");
        }

        return token;
    }

    public static void clearToken() {
        token = null;
    }
}