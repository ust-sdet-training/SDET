package com.ust.sdet.support;

public final class Config {

    private Config() {
    }

    public static String baseUrl() {
        return System.getProperty("baseUrl", "https://www.myntra.com/")
                .replaceAll("/$", "");
    }

//    public static String loginUrl() {
//        return baseUrl() + "/login";
//    }

    public static String catalogUrl() {
        return baseUrl() + "/catalog";
    }

    public static boolean headless() {
        return Boolean.parseBoolean(
                System.getProperty("headless", "false")
        );
    }
}