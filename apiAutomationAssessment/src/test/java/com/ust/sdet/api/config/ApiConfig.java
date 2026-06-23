package com.ust.sdet.api.config;

public class ApiConfig {
    static public final String BASE_URL = TestEnvironment.required(
        "baseUri"
    );
    static public final String API_KEY = TestEnvironment.required("api-key");
    static public String NAME = TestEnvironment.required("name");
    static public String EMAIL = TestEnvironment.required("email");
    static public String ROLE = TestEnvironment.required("role");
}
