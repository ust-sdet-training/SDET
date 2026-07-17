package com.tripstack.core;

import com.tripstack.client.AuthClient;
import com.tripstack.model.LoginRequest;
import com.tripstack.model.LoginResponse;

public final class TokenManager {

    private static final ThreadLocal<String> TOKEN = new ThreadLocal<>();

    private TokenManager() {
    }

    public static void setToken(String token) {
        if (token != null && !token.isBlank()) {
            TOKEN.set(token.trim());
        }
    }

    public static String getToken() {
        String token = TOKEN.get();
        if (token == null || token.isBlank()) {
            throw new IllegalStateException("Authentication token not found. Login first.");
        }
        return token;
    }

    public static void clear() {
        TOKEN.remove();
    }

    public static void authenticate(String email, String password) {
        LoginRequest request = new LoginRequest(email, password);
        LoginResponse response = new AuthClient().login(request);
        setToken(response.getToken());
    }

    public static LoginResponse getIdentity() {
        return new AuthClient().me(getToken());
    }
}
