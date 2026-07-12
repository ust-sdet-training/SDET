package com.shopkart.Config;

public class ApiConfig {

    public static String apply(){

        return System.getProperty(
                "baseUrl",
                System.getenv().getOrDefault("BASE_URL", "http://localhost:8080"));
    }
}
