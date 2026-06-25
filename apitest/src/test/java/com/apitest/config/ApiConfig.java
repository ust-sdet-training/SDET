package com.apitest.config;

public class ApiConfig {
    public static final String BASE_URL = System.getProperty(
        "baseUri",
        System.getenv().getOrDefault("BASE_URL", "https://jsonplaceholder.typicode.com")
    );
}


