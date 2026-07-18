package org.sdet.API_Framework.builders;

import org.sdet.API_Framework.config.ConfigManager;
import org.sdet.API_Framework.model.request.LoginRequest;

public final class LoginBuilder {

    private LoginBuilder() {
    }

    public static LoginRequest validLogin() {

        LoginRequest request = new LoginRequest();

        request.setEmail(ConfigManager.getEmail());
        request.setPassword(ConfigManager.getPassword());

        return request;
    }

    public static LoginRequest invalidPassword() {

        LoginRequest request = new LoginRequest();

        request.setEmail(ConfigManager.getEmail());
        request.setPassword("WrongPassword@123");

        return request;
    }

    public static LoginRequest invalidEmail() {

        LoginRequest request = new LoginRequest();

        request.setEmail("invalid@tripstack.test");
        request.setPassword(ConfigManager.getPassword());

        return request;
    }

    public static LoginRequest emptyCredentials() {

        LoginRequest request = new LoginRequest();

        request.setEmail("");
        request.setPassword("");

        return request;
    }

}