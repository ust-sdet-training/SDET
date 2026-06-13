package com.week2_gate2.resources.fixture;

import static com.week2_gate2.apiframework.config.TestEnvironment.*;

import com.week2_gate2.apiframework.config.TestEnvironment;

public class TestEnvironmentVariables {
    private static final String TOKEN_URL_CLIENT_ID = TestEnvironment.config("OAUTH_CLIENT_ID", "retail-ops-client");
    private static final String TOKEN_URL_CLIENT_SECRET = TestEnvironment.config("OAUTH_CLIENT_SECRET", "ops-secret");
    private static final String VIEWER_CLIENT_ID = TestEnvironment.config("OAUTH_VIEWER_CLIENT_ID", "retail-viewer-client");
    private static final String VIEWER_CLIENT_SECRET = TestEnvironment.config("OAUTH_VIEWER_CLIENT_SECRET", "viewer-secret");
    private static final String EXPIRED_CLIENT_ID = TestEnvironment.config("OAUTH_EXPIRED_CLIENT_ID", "retail-expired-client");
    private static final String EXPIRED_CLIENT_SECRET = TestEnvironment.config("OAUTH_EXPIRED_SECRET", "expired-secret");
    private static final String API_KEY = TestEnvironment.config("RETAIL_API_KEY", "retail-demo-key");

    public static String TOKEN_URL_CLIENT_ID() {
        return TOKEN_URL_CLIENT_ID;
    }

    public static String TOKEN_URL_CLIENT_SECRET() {
        return TOKEN_URL_CLIENT_SECRET;
    }

    public static String VIEWER_CLIENT_ID() {
        return VIEWER_CLIENT_ID;
    }

    public static String VIEWER_CLIENT_SECRET() {
        return VIEWER_CLIENT_SECRET;
    }

    public static String EXPIRED_CLIENT_ID() {
        return EXPIRED_CLIENT_ID;
    }

    public static String EXPIRED_CLIENT_SECRET() {
        return EXPIRED_CLIENT_SECRET;
    }

    public static String API_KEY() {
        return API_KEY;
    }
}
