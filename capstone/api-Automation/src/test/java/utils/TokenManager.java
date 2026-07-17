package utils;

import clients.AuthClient;

public class TokenManager {

    private static String token;

    public static String getToken() {

        if (token == null) {
            token = new AuthClient().login();
        }

        return token;
    }

    public static void setToken(String token) {
        TokenManager.token = token;
    }
}