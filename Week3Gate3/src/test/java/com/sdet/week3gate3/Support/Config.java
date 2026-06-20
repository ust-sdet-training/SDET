package com.sdet.week3gate3.Support;


public final class Config {

    private Config() {
    }

    public static String baseUrl() {
        return TestEnvironment.optional("baseUrl", "")
                .replaceAll("/$", "");
    }

    public static String catalogUrl() {
        return baseUrl() + "/catalog";
    }

    public static boolean headless() {
        return Boolean.parseBoolean(
                TestEnvironment.optional("headless", "")
        );
    }

    public static boolean headed() {
        return Boolean.parseBoolean(
                TestEnvironment.optional("headless", "")
        );
    }
    public static String loginUrl() {
        return baseUrl() + "/login";
    }
}