package com.week_2_gate_2.fixtures;
import com.week_2_gate_2.apiframework.config.TestEnvironment;

public class TestEnvironmentVariables {
    private static final String TOKEN_URL_CLIENT_ID = TestEnvironment.value("OAUTH_CLIENT_ID", "");
    private static final String TOKEN_URL_CLIENT_SECRET = TestEnvironment.value("OAUTH_CLIENT_SECRET", "");
    private static final String VIEWER_CLIENT_ID = TestEnvironment.value("OAUTH_VIEWER_CLIENT_ID", "");
    private static final String VIEWER_CLIENT_SECRET = TestEnvironment.value("OAUTH_VIEWER_CLIENT_SECRET", "");
    private static final String EXPIRED_CLIENT_ID = TestEnvironment.value("OAUTH_EXPIRED_CLIENT_ID", "");
    private static final String EXPIRED_CLIENT_SECRET = TestEnvironment.value("OAUTH_EXPIRED_CLIENT_SECRET", "");
    private static final String API_KEY = TestEnvironment.value("RETAIL_API_KEY", "retail-demo-key");

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
