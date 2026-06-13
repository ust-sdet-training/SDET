package com.ust.sdet.api.support;

public final class Credentials {

    private Credentials() {
    }

    public static final String BASE_URL = EnvCheck.required("BASE_URL");

    public static final String OAUTH_CLIENT_ID = EnvCheck.required("OAUTH_CLIENT_ID");
    public static final String OAUTH_CLIENT_SECRET = EnvCheck.required("OAUTH_CLIENT_SECRET");
    public static final String OAUTH_VIEWER_CLIENT_ID = EnvCheck.required("OAUTH_VIEWER_CLIENT_ID");
    public static final String OAUTH_VIEWER_CLIENT_SECRET = EnvCheck.required("OAUTH_VIEWER_CLIENT_SECRET");
    public static final String OAUTH_EXPIRED_CLIENT_ID = EnvCheck.required("OAUTH_EXPIRED_CLIENT_ID");
    public static final String OAUTH_EXPIRED_CLIENT_SECRET = EnvCheck.required("OAUTH_EXPIRED_CLIENT_SECRET");
    public static final String RETAIL_API_KEY = EnvCheck.required("RETAIL_API_KEY");
}