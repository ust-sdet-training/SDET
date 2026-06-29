package com.example.Selenium.config;

public final class Config {

    private Config(){}

    public static String baseUrl() {
        return "https://www.purplle.com";
    }

    public static boolean headless() {
        return false;
    }
}