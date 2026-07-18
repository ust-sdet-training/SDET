package org.sdet.API_Framework.utils;

import io.restassured.response.Response;
import org.sdet.API_Framework.builders.LoginBuilder;
import org.sdet.API_Framework.clients.AuthClient;

public class TokenManager {

    private static String token;

    private TokenManager() {
    }

    public static String getToken() {

        if (token == null) {

            AuthClient authClient = new AuthClient();

            Response response = authClient.login(
                    LoginBuilder.validLogin()
            );

            token = response.jsonPath().getString("token");
        }

        return token;
    }

    public static void clearToken() {
        token = null;
    }

    public static void setToken(String newToken) {
        token = newToken;
    }
}