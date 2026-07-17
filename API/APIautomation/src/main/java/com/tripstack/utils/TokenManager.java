package com.tripstack.utils;

import com.tripstack.client.AuthClient;
import com.tripstack.config.ConfigManager;

public class TokenManager {

    private static String cachedToken;

    public static synchronized String getToken() {
        if (cachedToken == null) {
            AuthClient authClient = new AuthClient();
            cachedToken = authClient.login(ConfigManager.EMAIL, ConfigManager.PASSWORD);
        }
        return cachedToken;
    }

    public static synchronized void clearToken() {
        cachedToken = null;
    }
}