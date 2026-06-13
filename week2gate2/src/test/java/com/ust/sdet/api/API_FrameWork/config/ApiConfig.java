package com.ust.sdet.api.API_FrameWork.config;

import com.ust.sdet.api.dbframework.support.TestEnvironment;

public final class ApiConfig {

    private ApiConfig() {
    }

    public static final String BASE_URL =
            TestEnvironment.optional(
                    "BASE_URL",
                    "http://localhost:4000"
            );
}