package com.gamagate1.config;

public class ApiConfig {
    public static final String BASE_URL = System.getProperty(
        "baseUri",
        System.getenv().getOrDefault("BASE_URL", "https://restful-booker.herokuapp.com/apidoc/index.html")
    );
    public static final String API_KEY = "special-key";
}
