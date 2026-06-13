package com.ust.sdet.Api_Framework.config;

import com.ust.sdet.dbframework.support.TestEnvironment;

public final class AuthenticationConfig {

    private AuthenticationConfig() {
    }

    public static final String OPS_CLIENT_ID =
            TestEnvironment.required("OPS_CLIENT_ID");

    public static final String OPS_CLIENT_SECRET =
            TestEnvironment.required("OPS_CLIENT_SECRET");

    public static final String VIEWER_CLIENT_ID =
            TestEnvironment.optional("VIEWER_CLIENT_ID", "");

    public static final String VIEWER_CLIENT_SECRET =
            TestEnvironment.optional("VIEWER_CLIENT_SECRET", "");

    public static final String EXPIRED_CLIENT_ID =
            TestEnvironment.optional("EXPIRED_CLIENT_ID", "");

    public static final String EXPIRED_CLIENT_SECRET =
            TestEnvironment.optional("EXPIRED_CLIENT_SECRET", "");

    public static final String RETAIL_API_KEY =
            TestEnvironment.optional("RETAIL_API_KEY", "");

    public static final String CART_SESSION =
            TestEnvironment.optional(
                    "CART_SESSION",
                    "classroom-session"
            );
}