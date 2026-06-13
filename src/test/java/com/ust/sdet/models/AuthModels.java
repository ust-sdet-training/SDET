package com.ust.sdet.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public final class AuthModels {

    private AuthModels() {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LoginRequest {
        private String email;
        private String password;

        public LoginRequest() {}

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OAuthRequest {
        private String grant_type;
        private String client_id;
        private String client_secret;

        public OAuthRequest() {}

        public OAuthRequest(String grantType, String clientId, String clientSecret) {
            this.grant_type = grantType;
            this.client_id = clientId;
            this.client_secret = clientSecret;
        }

        public String getGrantType() {
            return grant_type;
        }

        public String getClientId() {
            return client_id;
        }

        public String getClientSecret() {
            return client_secret;
        }
    }
}