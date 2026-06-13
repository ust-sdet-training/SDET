package com.ust.sdet.auth;

import com.ust.sdet.config.TestConfig;
import com.ust.sdet.models.AuthModels;
import com.ust.sdet.specs.RequestSpecs;
import com.ust.sdet.specs.ResponseSpecs;
import static io.restassured.RestAssured.given;

public final class Auth {

    public static String loginToken() {
        return login(new AuthModels.LoginRequest(TestConfig.EMAIL, TestConfig.PASSWORD));
    }
    public static String adminToken() {
        return login(new AuthModels.LoginRequest(TestConfig.ADMIN_EMAIL, TestConfig.ADMIN_PASSWORD));
    }

    private static String login(AuthModels.LoginRequest request) {

        return given()
                .spec(RequestSpecs.jsonRequestSpec())
                .body(request)
                .when()
                .post("/api/auth/login")
                .then()
                .spec(ResponseSpecs.ok200Spec())
                .extract()
                .path("token");

    }


    public static String oauthToken() {
        return oauth(
                new AuthModels.OAuthRequest(TestConfig.GRANT_TYPE, TestConfig.CLIENT_ID, TestConfig.CLIENT_SECRET));
    }


    public static String viewerOauthToken() {
        return oauth(new AuthModels.OAuthRequest(TestConfig.GRANT_TYPE, TestConfig.VIEWER_CLIENT_ID, TestConfig.VIEWER_CLIENT_SECRET));

    }


    public static String expiredOauthToken() {
        return oauth(
                new AuthModels.OAuthRequest(TestConfig.GRANT_TYPE, TestConfig.EXPIRED_CLIENT_ID, TestConfig.EXPIRED_CLIENT_SECRET));

    }


    private static String oauth(AuthModels.OAuthRequest request) {
        return given()
                .spec(RequestSpecs.formRequestSpec())
                .formParam("grant_type", request.getGrantType())
                .formParam("client_id", request.getClientId())
                .formParam("client_secret", request.getClientSecret())
                .when()
                .post("/api/oauth/token")
                .then()
                .statusCode(200)
                .extract()
                .path("access_token");

    }


    public static String invalidToken() {
        return "invalid-token";
    }
}