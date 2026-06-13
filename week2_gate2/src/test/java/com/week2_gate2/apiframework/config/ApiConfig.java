package com.week2_gate2.apiframework.config;

public class ApiConfig {
    public static final String BASE_URL = System.getProperty(
            "baseUrl",
            System.getenv().getOrDefault("BASE_URL", "http://localhost:4000")
    );
}
