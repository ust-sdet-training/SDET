package com.ust.sdet.api.ApiFlow.Config;

import com.ust.sdet.api.DbFramework.Support.TestEnvironment;

public final class AuthConfig {

    private AuthConfig() {
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