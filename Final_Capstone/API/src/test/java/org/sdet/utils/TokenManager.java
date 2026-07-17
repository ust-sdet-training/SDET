package org.sdet.utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.sdet.clients.AuthClient;

public class TokenManager {

    private static String token;

    public static String getToken() {

        if (token == null) {

            AuthClient authClient = new AuthClient();

            Response response = authClient.login(LoginRequestBuilder.defaultLogin());

            JsonPath jsonPath = response.jsonPath();

            token = jsonPath.getString("token");
        }

        return token;
    }

}