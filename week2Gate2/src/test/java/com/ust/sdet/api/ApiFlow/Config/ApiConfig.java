package com.ust.sdet.api.ApiFlow.Config;

import com.ust.sdet.api.DbFramework.Support.TestEnvironment;

public final class ApiConfig {

    private ApiConfig() {
    }

    public static final String BASE_URL =
            TestEnvironment.optional(
                    "BASE_URL",
                    "http://localhost:4000"
            );
}