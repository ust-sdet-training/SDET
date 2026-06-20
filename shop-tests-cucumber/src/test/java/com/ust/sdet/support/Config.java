package com.ust.sdet.support;

public final class Config {
    private Config() {
    }

    public static String baseUrl() {
        return TestEnvironment.optional("baseUrl", "http://localhost:5173").replaceAll("/$", "");
    }

    public static String catalogUrl() {
        return baseUrl() + "/catalog";
    }

    public static boolean headless() {
        return Boolean.parseBoolean(TestEnvironment.optional("headless", "false"));
    }
}
