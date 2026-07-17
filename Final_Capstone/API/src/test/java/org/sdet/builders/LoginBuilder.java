package org.sdet.builders;

import org.sdet.config.Secrets;
import org.sdet.model.request.LoginRequest;

public class LoginBuilder {

    private LoginRequest request;

    public LoginBuilder() {
        request = new LoginRequest();
    }

    public LoginBuilder setEmail(String email) {
        request.setEmail(email);
        return this;
    }

    public LoginBuilder setPassword(String password) {
        request.setPassword(password);
        return this;
    }

    public LoginRequest build() {
        return request;
    }

    // Uses credentials from config.properties
    public static LoginRequest defaultLogin() {
        return new LoginBuilder()
                .setEmail(Secrets.email())
                .setPassword(Secrets.password())
                .build();
    }
}