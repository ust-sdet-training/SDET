package com.api.config;

import com.api.data.Secrets;

public class ApiConfig {

    public ApiConfig() {
    }

    public static final String API_BASE_URL =
            System.getenv("BASE_URL");
}
