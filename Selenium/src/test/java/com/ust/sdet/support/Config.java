package com.ust.sdet.support;

public class Config {
    private Config() {}

    public static String baseUrl() {
        return System.getProperty("BASE_URL", "https://www.booking.com/")
                .replaceAll("/$", "");
    }

//    public static String catalogUrl() {
//        return baseUrl() + "/catalog";
//    }

    public static boolean headless() {
        return Boolean.parseBoolean((System.getProperty("headless", "false")));
    }

}
