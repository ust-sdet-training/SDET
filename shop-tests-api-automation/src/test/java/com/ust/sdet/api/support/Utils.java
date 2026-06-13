package com.ust.sdet.api.support;

import static com.ust.sdet.api.specification.LoginSpec.oauthReqSpec;
import static com.ust.sdet.api.support.TestEnvironment.config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class Utils {
    public static final String TOKEN_URL_CLIENT_ID = config("OAUTH_CLIENT_ID", "retail-ops-client");
    public static final String TOKEN_URL_CLIENT_SECRET = config("OAUTH_CLIENT_SECRET", "ops-secret");
    public static final String VIEWER_URL_CLIENT_ID = config("OAUTH_VIEWER_CLIENT_ID", "retail-viewer-client");
    public static final String VIEWER_URL_CLIENT_SECRET = config("OAUTH_VIEWER_CLIENT_SECRET", "viewer-secret");
    public static final String EXPIRED_URL_CLIENT_ID = config("OAUTH_EXPIRED_CLIENT_ID", "viewer-secret");
    public static final String EXPIRED_URL_CLIENT_SECRET = config("OAUTH_EXPIRED_CLIENT_SECRET", "expired-secret");
    public static final String API_KEY = config("RETAIL_API_KEY", "retail-demo-key");

    public static final String BASE_URL = "http://localhost:4000";

    public static String token() {
        return given()
                .spec(oauthReqSpec(TOKEN_URL_CLIENT_ID, TOKEN_URL_CLIENT_SECRET))
                .when()
                .post("/oauth/token")
                .then()
                .body("access_token", notNullValue())
                .extract()
                .jsonPath()
                .getString("access_token");
    }

    public static String viewerToken() {
        return given()
                .spec(oauthReqSpec(VIEWER_URL_CLIENT_ID, VIEWER_URL_CLIENT_SECRET))
                .when()
                .post("/oauth/token")
                .then()
                .body("access_token", notNullValue())
                .extract()
                .jsonPath()
                .getString("access_token");
    }

    public static String expiredToken() {
        return given()
                .spec(oauthReqSpec(EXPIRED_URL_CLIENT_ID, EXPIRED_URL_CLIENT_SECRET))
                .when()
                .post("/oauth/token")
                .then()
                .body("access_token", notNullValue())
                .extract()
                .jsonPath()
                .getString("access_token");
    }
}
