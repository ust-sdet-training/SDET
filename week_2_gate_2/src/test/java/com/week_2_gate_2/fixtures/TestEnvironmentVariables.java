package com.week_2_gate_2.fixtures;
import com.week_2_gate_2.apiframework.config.TestEnvironment;

import io.github.cdimascio.dotenv.Dotenv;

public class TestEnvironmentVariables {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String TOKEN_URL_CLIENT_ID = dotenv.get("OAUTH_CLIENT_ID", "retail-ops-client");
    private static final String TOKEN_URL_CLIENT_SECRET = dotenv.get("OAUTH_CLIENT_SECRET");
    private static final String VIEWER_CLIENT_ID = dotenv.get("OAUTH_VIEWER_CLIENT_ID", "retail-viewer-client");
    private static final String VIEWER_CLIENT_SECRET = dotenv.get("OAUTH_VIEWER_CLIENT_SECRET");
    private static final String EXPIRED_CLIENT_ID = dotenv.get("OAUTH_EXPIRED_CLIENT_ID", "retail-expired-client");
    private static final String EXPIRED_CLIENT_SECRET = dotenv.get("OAUTH_EXPIRED_CLIENT_SECRET");
    private static final String API_KEY = dotenv.get("RETAIL_API_KEY", "retail-demo-key");

    public static String TOKEN_URL_CLIENT_ID(){
        return TOKEN_URL_CLIENT_ID;
    }

    public static String TOKEN_URL_CLIENT_SECRET(){
        return TOKEN_URL_CLIENT_SECRET;
    }

    public static String VIEWER_CLIENT_ID(){
        return VIEWER_CLIENT_ID;
    }

    public static String VIEWER_CLIENT_SECRET(){
        return VIEWER_CLIENT_SECRET;
    }

    public static String EXPIRED_CLIENT_ID(){
        return EXPIRED_CLIENT_ID;
    }

    public static String EXPIRED_CLIENT_SECRET(){
        return EXPIRED_CLIENT_SECRET;
    }

    public static String API_KEY(){
        return API_KEY;
    }

}
