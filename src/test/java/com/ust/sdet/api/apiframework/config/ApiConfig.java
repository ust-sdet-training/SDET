package com.ust.sdet.api.apiframework.config;

public final class ApiConfig {

    private ApiConfig() {}

    public static String baseUrl() {
        return ConfigReader.get("BASE_URL", "http://localhost:4000");
    }
}
