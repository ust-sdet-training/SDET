package com.api.config;

import com.api.data.Secrets;

public class ApiConfig {

    public ApiConfig() {
    }

    public static final String API_BASE_URL =
            Secrets.get("BASE_URL");
}
