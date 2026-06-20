package com.ust.sdet.support;

public class Config {
    private Config() {}

    public static String baseUrl() {
        return System.getProperty("BASE_URL", "http://localhost:5173")
                .replaceAll("/$", "");
    }

    public static String catalogUrl() {
        return baseUrl() + "/catalog";
    }

    public static String cartUrl() {
        return baseUrl() + "/cart";
    }

    public static boolean headless() {
        return Boolean.parseBoolean(System.getProperty("headless", "false"));
    }
}
